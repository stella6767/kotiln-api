package com.example.kotilnbook.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.p6spy.engine.spy.P6SpyOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Configuration
@EnableJpaAuditing
class JpaConfig {

    @PersistenceContext
    private val entityManager: EntityManager? = null

    @PostConstruct
    fun setLogMessgeFormat(){
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spyPrettySqlFormatter::class.java.getName()

    }


    @Bean
    fun objectMapper():ObjectMapper{
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //아직 불러오지 않은 엔티티에 대해 null값을 내려주는 모듈이다. lazy loading
        objectMapper.registerModule(Hibernate5Module())
        objectMapper.registerModule(KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        return objectMapper
    }


}