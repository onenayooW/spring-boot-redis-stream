package com.example.springbootredisstream.service

import com.example.springbootredisstream.model.Product
import com.example.springbootredisstream.util.RedisTopicKey
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class ProductPublisher (
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
){
    private val objectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


    fun publishEvent(product: Product) {
        val record: ObjectRecord<String, String> = StreamRecords.newRecord()
            .ofObject(objectMapper.writeValueAsString(product))
            .withStreamKey(RedisTopicKey.PRODUCT_TOPIC)

        redisTemplate.opsForStream<String, String>()
            .add(record)
            .subscribe { println("send success data: $it") }
    }
}