package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.entities.orgs.DBOrgProjects
import com.mutualmobile.praxisspringboot.repositories.orgs.OrgProjectsRepository
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrganizationProjectServiceImpl : OrganizationProjectService {
    @Autowired
    lateinit var orgProjectsRepository: OrgProjectsRepository

    override fun createProject(organizationProject: OrganizationProject): OrganizationProject {
        val project = orgProjectsRepository.save(organizationProject.toDbOrgProject())
        return project.toOrgProject()
    }

    override fun getAllProjects(organizationId: String, offset: Int, limit: Int): Pair<Int, List<OrganizationProject>> {
        val allProjects = orgProjectsRepository.findAllByOrganizationId(
            organizationId = organizationId,
            pageable = PageRequest.of(offset, limit)
        )
        return Pair(allProjects.totalPages,allProjects.content.map { it.toOrgProject() })
    }

    override fun updateProject(organizationProject: OrganizationProject): Boolean {
        val doesCurrentProjectExist = orgProjectsRepository.findByIdOrNull(organizationProject.id) != null
        return if (!doesCurrentProjectExist) {
            false
        } else try {
            orgProjectsRepository.save(
                organizationProject.toDbOrgProject().apply {
                    this.id = organizationProject.id ?: throw Exception()
                }
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun deleteProject(projectId: String): Boolean {
        val doesCurrentProjectExist = orgProjectsRepository.existsById(projectId)
        return if (!doesCurrentProjectExist) {
            false
        } else {
            orgProjectsRepository.deleteById(projectId)
            true
        }
    }

    override fun getProjectById(projectId: String): OrganizationProject? {
        return orgProjectsRepository.findByIdOrNull(id = projectId)?.toOrgProject()
    }

    override fun checkIfProjectExists(projectId: String): Boolean {
        return orgProjectsRepository.existsById(projectId)
    }
}

fun OrganizationProject.toDbOrgProject() = DBOrgProjects(
    name = name,
    client = client,
    startDate = startDate,
    endDate = endDate,
    isIndefinite = isIndefinite,
    organizationId = organizationId
).apply {
    this@toDbOrgProject.id?.let { nnId ->
        this.id = nnId
    }
}

fun DBOrgProjects.toOrgProject() = OrganizationProject(
    id = id,
    name = name,
    client = client,
    startDate = startDate,
    endDate = endDate,
    isIndefinite = isIndefinite,
    organizationId = organizationId
)