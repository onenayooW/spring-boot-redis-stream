package com.example.springbootredisstream.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Product (
    @JsonProperty("name")
    val name: String,
    @JsonProperty("age")
    val age: Int,
)