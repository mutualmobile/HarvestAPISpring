package com.mutualmobile.praxisspringboot

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition
class PraxisSpringBootApplication : SpringBootServletInitializer() {

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(PraxisSpringBootApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<PraxisSpringBootApplication>(*args)
}
