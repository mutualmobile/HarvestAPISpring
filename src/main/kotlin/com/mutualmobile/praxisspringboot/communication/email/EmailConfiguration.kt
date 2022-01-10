package com.mutualmobile.praxisspringboot.communication.email

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.thymeleaf.templateresolver.ITemplateResolver
import org.thymeleaf.spring5.SpringTemplateEngine
import org.springframework.context.support.ResourceBundleMessageSource
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@ComponentScan(basePackages = ["com.mutualmobile.praxisspringboot.communication.email"])
@PropertySource(value = ["classpath:application.properties"])
class EmailConfiguration {
    @Value("\${spring.mail.host}")
    private val mailServerHost: String? = null

    @Value("\${spring.mail.port}")
    private val mailServerPort: Int? = null

    @Value("\${spring.mail.username}")
    private val mailServerUsername: String? = null

    @Value("\${spring.mail.password}")
    private val mailServerPassword: String? = null

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val mailServerAuth: String? = null

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private val mailServerStartTls: String? = null

    @Value("\${spring.mail.templates.path}")
    private val mailTemplatesPath: String? = null

    @get:Bean
    val javaMailSender: JavaMailSender
        get() {
            val mailSender = JavaMailSenderImpl()
            mailSender.host = mailServerHost
            mailSender.port = mailServerPort!!
            mailSender.username = mailServerUsername
            mailSender.password = mailServerPassword
            val props = mailSender.javaMailProperties
            props["mail.transport.protocol"] = "smtp"
            props["mail.smtp.auth"] = mailServerAuth
            props["mail.smtp.starttls.enable"] = mailServerStartTls
            props["mail.debug"] = "true"
            return mailSender
        }

    @Bean
    fun thymeleafTemplateEngine(templateResolver: ITemplateResolver?): SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver)
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("mailMessages")
        templateEngine.setTemplateEngineMessageSource(messageSource)
        return templateEngine
    }

    @Bean
    fun thymeleafClassLoaderTemplateResolver(): ITemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "$mailTemplatesPath/"
        templateResolver.suffix = ".html"
        templateResolver.setTemplateMode("HTML")
        templateResolver.characterEncoding = "UTF-8"
        return templateResolver
    }
}