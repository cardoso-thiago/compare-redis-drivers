package br.com.cardoso.redisson

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.time.Duration

@SpringBootApplication
@EnableCaching
class DemoLettuceApplication {

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        val clientConfig: LettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.ofSeconds(2))
            .shutdownTimeout(Duration.ZERO)
            .build()
        val lettuceConnectionFactory = LettuceConnectionFactory(
            RedisStandaloneConfiguration("localhost", 6379),
            clientConfig
        )
        lettuceConnectionFactory.eagerInitialization = true
        return lettuceConnectionFactory
    }
}

fun main(args: Array<String>) {
    runApplication<DemoLettuceApplication>(*args)
}
