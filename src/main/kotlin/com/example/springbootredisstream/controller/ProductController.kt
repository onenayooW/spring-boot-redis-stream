package com.example.springbootredisstream.controller

import com.example.springbootredisstream.model.Product
import com.example.springbootredisstream.model.User
import com.example.springbootredisstream.service.ProductPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController (
    private val productPublisher: ProductPublisher
){
    @GetMapping("/product/send")
    fun sendMessage() {
        productPublisher.publishEvent(Product("product1", 1))
    }
}