package com.example.springbootredisstream.listener

import com.example.springbootredisstream.model.User
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Service
import java.net.InetAddress

@Service
class UserConsumer(
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
) : StreamListener<String, ObjectRecord<String, String>> {


    private val objectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


    override fun onMessage(record: ObjectRecord<String, String>) {
        println("${InetAddress.getLocalHost().hostName} - consumed: ${record.value}")
        println(record.value)
        val data = objectMapper.readValue(record.value, User::class.java)
        println(data)
        println("user --->$data")
        redisTemplate.opsForStream<String, String>()
            .delete(record)
            .subscribe { println("user --->$it") }
    }

}

//
//@Component
//class MyStreamConsumer(
//    private val redisTemplate: RedisTemplate<String, ObjectRecord<String, String>>,
//) {
//    @EventListener(ApplicationReadyEvent::class)
//    fun consume() {
//
//        while (true) {
//            val streamRecords = redisTemplate
//                .opsForStream<String, ObjectRecord<String, String>>()
//                .read(
//                    StreamReadOptions.empty().block(Duration.ofSeconds(1)),
//                    StreamOffset.create("my-stream", ReadOffset.lastConsumed())
//                )
//
//            streamRecords.
//
//            if (streamRecords.isNotEmpty()) {
//                streamRecords.forEach { record ->
//                    println("Received record: $record")
//                }
//            }
//        }
//    }
//}