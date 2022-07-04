package com.example.kotilnbook.config

import com.p6spy.engine.spy.P6SpyOptions
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.annotation.PostConstruct


@Configuration
@EnableJpaAuditing
class JpaConfig {

    @PostConstruct
    fun setLogMessgeFormat(){
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spyPrettySqlFormatter::class.java.getName()

    }


}