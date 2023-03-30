package com.example.springbootredisstream.listener

import com.example.springbootredisstream.model.Product
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class ProductConsumer (
    private val redisTemplate: ReactiveRedisTemplate<String, String>
): StreamListener<String, ObjectRecord<String, String>> {

//    @Autowired
//    private lateinit var redisTemplate: ReactiveRedisTemplate<String, String>

    private val objectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    override fun onMessage(record: ObjectRecord<String, String>) {
        println(record.value)
        val data = objectMapper.readValue(record.value, Product::class.java)
        println("product--->$data")

        redisTemplate.opsForStream<String, String>()
            .delete(record)
            .subscribe { println("product--->$it") }
    }

}