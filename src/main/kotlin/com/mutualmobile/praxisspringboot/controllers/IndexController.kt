package com.mutualmobile.praxisspringboot.controllers

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
class IndexController {
    @GetMapping("/")
    fun index(): String {
        return "Hello there! I'm running."
    }
}