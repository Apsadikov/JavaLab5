package ru.itis.hateoas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.hateoas.model.*;
import ru.itis.hateoas.repository.OrderRepository;
import ru.itis.hateoas.repository.ProductRepository;
import ru.itis.hateoas.repository.ReviewRepository;
import ru.itis.hateoas.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderRepository orderRepository,
                             ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addReview(Long userId, Long productId, String text, int stars) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        List<Order> orders = orderRepository.getOrdersByUser(user);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!orders.isEmpty() && optionalProduct.isPresent() && isProductOrdered(orders, productId)) {
            reviewRepository.save(Review.builder()
                    .product(optionalProduct.get())
                    .review(text)
                    .stars(stars)
                    .user(user)
                    .build());
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isProductOrdered(List<Order> orders, Long productId) {
        for (Order order : orders) {
            for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
                if (orderedProduct.getProduct().getId().equals(productId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
