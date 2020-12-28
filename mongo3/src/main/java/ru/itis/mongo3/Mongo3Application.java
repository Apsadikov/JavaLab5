package ru.itis.mongo3;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Mongo3Application {

    public static void main(String[] args) {
        SpringApplication.run(Mongo3Application.class, args);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(), "project");
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        return MongoClients.create().getDatabase("project");
    }

}
