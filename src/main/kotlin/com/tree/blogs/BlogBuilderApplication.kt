package com.tree.blogs


import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(  exclude=[DataSourceAutoConfiguration::class,MongoAutoConfiguration::class])
class CourseBuilderApplication

fun main(args: Array<String>) {
	runApplication<CourseBuilderApplication>(*args)
}
