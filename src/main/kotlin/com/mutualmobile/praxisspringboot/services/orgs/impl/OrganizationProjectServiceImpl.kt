package com.mutualmobile.praxisspringboot.services.orgs.impl

import com.mutualmobile.praxisspringboot.data.models.orgs.OrganizationProject
import com.mutualmobile.praxisspringboot.entities.orgs.DBOrgProjects
import com.mutualmobile.praxisspringboot.repositories.orgs.OrgProjectsRepository
import com.mutualmobile.praxisspringboot.services.orgs.OrganizationProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationProjectServiceImpl: OrganizationProjectService {
    @Autowired
    lateinit var orgProjectsRepository: OrgProjectsRepository

    override fun createProject(organizationProject: OrganizationProject): OrganizationProject {
        val project = orgProjectsRepository.save(organizationProject.toDbOrgProject())
        return project.toOrgProject()
    }
}

private fun OrganizationProject.toDbOrgProject() = DBOrgProjects(
    name, client, startDate, endDate, isIndefinite
)

private fun DBOrgProjects.toOrgProject() = OrganizationProject(
    name, client, startDate, endDate, isIndefinite
)