package com.example.springbootredisstream.controller

import com.example.springbootredisstream.model.User
import com.example.springbootredisstream.service.UserPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val movieEventPublisher: UserPublisher) {


    @GetMapping("/user/send")
    fun sendMessage() {
        movieEventPublisher.publishEvent(User("test", 1))
    }
}