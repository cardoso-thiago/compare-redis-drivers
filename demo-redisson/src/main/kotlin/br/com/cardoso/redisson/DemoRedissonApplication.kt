package br.com.cardoso.redisson

import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.redisson.spring.cache.CacheConfig
import org.redisson.spring.cache.RedissonSpringCacheManager
import org.redisson.spring.data.connection.RedissonConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.core.RedisTemplate


@SpringBootApplication
@EnableCaching
class DemoRedissonApplication {

    @Bean
    fun config(): Config {
        val config = Config()
        config.useSingleServer().address = "redis://localhost:6379"
        return config
    }

    @Bean
    fun redissonConnectionFactory(config: Config): RedissonConnectionFactory {
        return RedissonConnectionFactory(config)
    }

    @Bean
    fun cacheManager(redissonClient: RedissonClient): CacheManager? {
        val config: MutableMap<String, CacheConfig> = HashMap()
        config["Artist"] = CacheConfig()
        return RedissonSpringCacheManager(redissonClient, config)
    }

    @Bean
    fun redisTemplate(redissonConnectionFactory: RedissonConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate: RedisTemplate<String, Any> = RedisTemplate()
        redisTemplate.setConnectionFactory(redissonConnectionFactory)
        return redisTemplate
    }
}

fun main(args: Array<String>) {
    runApplication<DemoRedissonApplication>(*args)
}
