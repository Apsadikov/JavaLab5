package ru.itis.hateoas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itis.hateoas.service.ReviewService;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/products/{productId}/addReview", method = RequestMethod.POST)
    public void publish(@PathVariable("productId") Long productId, @RequestParam("userId") Long userId,
                        @RequestParam("review") String text, @RequestParam int stars) {
        reviewService.addReview(userId, productId, text, stars);
    }
}
