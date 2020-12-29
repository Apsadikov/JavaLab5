package ru.itis.hateoas.service;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void addReview(Long userId, Long productId, String text,int stars);
}
