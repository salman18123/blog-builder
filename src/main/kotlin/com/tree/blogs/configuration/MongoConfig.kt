package com.tree.blogs.configuration

import com.mongodb.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.mapping.Document
import java.util.concurrent.TimeUnit


@Configuration
class MongoConfig(
    @Value("\${spring.data.mongodb.uri}")
     val mongoDbUri:String,
    @Value("\${spring.data.mongodb.maxWaitTime:100}")
    val maxWaitTime:Long,
    @Value("\${spring.data.mongodb.connectionPoolMinSize:5}")
     val connectionPoolMinSize: Int,
    @Value("\${spring.data.mongodb.connectionPoolMaxSize:50}")
     val connectionPoolMaxSize: Int,
    @Value("\${spring.data.mongodb.idleTimeOutInSeconds:120}")
     val idleTimeOut:Int,
    @Value("\${spring.data.mongodb.maxLifeTimeInHours:8}")
    val maxLifeTime:Int,
    @Value("\${spring.data.mongodb.connectTimeoutInMs:100}")
    val connectTimeout:Int,
    @Value("\${spring.data.mongodb.readTimeoutInMs:60000}")
    val readTimeout :Int
) : AbstractMongoClientConfiguration() {


    @Bean
    fun transactionManager(dbFactory:MongoDatabaseFactory):MongoTransactionManager {
        return MongoTransactionManager(dbFactory);
    }

    override fun mongoClient():MongoClient {
        val connectionString = mongoDbUri
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()
        return MongoClients.create(settings);
    }

override fun  autoIndexCreation():Boolean {
    return true
}

    override fun getDatabaseName(): String {
        return "blog-builder";
    }
}