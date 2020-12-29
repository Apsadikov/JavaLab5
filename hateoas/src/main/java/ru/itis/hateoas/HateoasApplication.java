package ru.itis.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.itis.hateoas.model.*;
import ru.itis.hateoas.repository.*;

@SpringBootApplication
public class HateoasApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HateoasApplication.class, args);
        UserRepository userRepository = context.getBean(UserRepository.class);
        ShopRepository shopRepository = context.getBean(ShopRepository.class);
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        OrderRepository orderRepository = context.getBean(OrderRepository.class);
        OrderedProductRepository orderedProductRepository = context.getBean(OrderedProductRepository.class);
        User user = userRepository.save(User.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivan@gmail.com")
                .password("sha1")
                .build());
        User user2 = userRepository.save(User.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .email("petr@gmail.com")
                .password("sha1")
                .build());

        Shop shop = shopRepository.save(Shop.builder()
                .name("New Shop")
                .user(user)
                .build());

        Product product = productRepository.save(Product.builder()
                .name("product 1")
                .price(100d)
                .shop(shop)
                .build());
        productRepository.save(Product.builder()
                .name("product 2")
                .price(200d)
                .shop(shop)
                .build());
        productRepository.save(Product.builder()
                .name("product 3")
                .price(300d)
                .shop(shop)
                .build());

        Order order = orderRepository.save(Order.builder()
                .shop(shop)
                .totalPrice(100d)
                .user(user2)
                .build());
        orderedProductRepository.save(OrderedProduct.builder()
                .price(100d)
                .product(product)
                .order(order)
                .build());
    }
}
