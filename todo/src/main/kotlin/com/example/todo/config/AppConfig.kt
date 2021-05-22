package com.example.todo.config

import com.example.todo.database.TodoDatabase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    
    @Bean(initMethod = "init") // init 메서드 호출
    fun todoDatabase(): TodoDatabase {
        return TodoDatabase()
    }

}