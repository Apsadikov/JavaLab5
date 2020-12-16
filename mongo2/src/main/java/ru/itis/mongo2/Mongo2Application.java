package ru.itis.mongo2;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@Configuration
public class Mongo2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Mongo2Application.class, args);

        //MongoTemplate

//        ProductTemplateRepositoryImpl productTemplateRepository = context.getBean(ProductTemplateRepositoryImpl.class);
//        productTemplateRepository.findAll().forEach(System.out::println);
//
//        Optional<Product> optionalProduct = productTemplateRepository.findById("5fca4f36d587b9598686297d");
//        optionalProduct.ifPresent(System.out::println);
//
//        productTemplateRepository.delete("5fca4f36d587b9598686297d");

        //MongoDriver

//        ProductDriverRepositoryImpl productDriverRepository = context.getBean(ProductDriverRepositoryImpl.class);
//        productDriverRepository.findAll().forEach(System.out::println);
//
//        productDriverRepository.delete("5fca4f36d587b9598686297b");
//
//        productDriverRepository.save(Product.builder()
//                .name("test 1")
//                .price(100d)
//                .category("Toys")
//                .build());
//
//        System.out.println(productDriverRepository.findById("5fd9d83ab68ffc73a22aa8fb"));
//
//        productDriverRepository.updatePrice("5fd9d83ab68ffc73a22aa8fb", 999d);
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
