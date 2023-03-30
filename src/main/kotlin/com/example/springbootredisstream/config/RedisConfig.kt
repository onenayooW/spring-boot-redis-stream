package com.example.springbootredisstream.config

import com.example.springbootredisstream.listener.ProductConsumer
import com.example.springbootredisstream.listener.UserConsumer
import com.example.springbootredisstream.util.RedisTopicKey.PRODUCT_GROUP_1_TOPIC
import com.example.springbootredisstream.util.RedisTopicKey.PRODUCT_TOPIC
import com.example.springbootredisstream.util.RedisTopicKey.USER_GROUP_1_TOPIC
import com.example.springbootredisstream.util.RedisTopicKey.USER_TOPIC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription
import java.time.Duration

@Configuration
class RedisConfig {

    @Autowired
    private lateinit var redisTemplate: ReactiveRedisTemplate<String, String>

    @Bean
    fun streamMessageListenerContainerOption():
            StreamMessageListenerContainer.
            StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> =
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions
            .builder()
            .pollTimeout(Duration.ofMinutes(1))
            .targetType(String::class.java)
            .build()

    @Bean
    fun createListenerContainer(
        redisConnectionFactory: RedisConnectionFactory,
        options: StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>>,
    ): StreamMessageListenerContainer<String, ObjectRecord<String, String>> =
        StreamMessageListenerContainer.create(redisConnectionFactory, options)

    @Bean
    fun userSubscription(redisConnectionFactory: RedisConnectionFactory): Subscription? {
        val options = streamMessageListenerContainerOption()
        val listenerContainer = createListenerContainer(redisConnectionFactory, options)

        val subscription = listenerContainer.receiveAutoAck(
            Consumer.from(USER_GROUP_1_TOPIC, "userSubscription-2"),
            StreamOffset.create(USER_TOPIC, ReadOffset.lastConsumed()),
            UserConsumer(redisTemplate)
        )
        listenerContainer.start()
        return subscription
    }

    @Bean
    fun productSubscription(redisConnectionFactory: RedisConnectionFactory): Subscription? {
        val options = streamMessageListenerContainerOption()
        val listenerContainer = createListenerContainer(redisConnectionFactory, options)

        val subscription = listenerContainer.receiveAutoAck(
            Consumer.from(PRODUCT_GROUP_1_TOPIC, "productSubscription-1"),
            StreamOffset.create(PRODUCT_TOPIC, ReadOffset.lastConsumed()),
            ProductConsumer(redisTemplate)
        )
        listenerContainer.start()
        return subscription
    }

}
