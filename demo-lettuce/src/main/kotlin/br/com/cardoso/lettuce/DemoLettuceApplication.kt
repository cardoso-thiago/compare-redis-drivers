package br.com.cardoso.lettuce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class DemoLettuceApplication

fun main(args: Array<String>) {
	runApplication<DemoLettuceApplication>(*args)
}
