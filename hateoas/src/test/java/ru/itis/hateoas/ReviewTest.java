package ru.itis.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.hateoas.model.Order;
import ru.itis.hateoas.model.OrderedProduct;
import ru.itis.hateoas.model.Product;
import ru.itis.hateoas.model.User;
import ru.itis.hateoas.repository.OrderRepository;
import ru.itis.hateoas.repository.ProductRepository;
import ru.itis.hateoas.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ReviewTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .id(1L)
                .build();
        Product product = Product.builder()
                .id(1L)
                .build();
        Order order = orderRepository.save(Order.builder()
                .totalPrice(100d)
                .user(user)
                .orderedProducts(Collections.singleton(OrderedProduct.builder()
                        .id(1L)
                        .product(product)
                        .build()))
                .build());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.getOrdersByUser(user)).thenReturn(Collections.singletonList(order));
    }

    @Test
    public void addReview() throws Exception {
        mockMvc.perform(post("/products/1/addReview")
                .param("userId", "1")
                .param("review", "text")
                .param("stars", "1"))
                .andDo(print())
                .andExpect(status().isOk())
    }
}
