package ru.itis.mongo3.controller;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.mongo3.dto.CommentDto;
import ru.itis.mongo3.dto.ProductDto;
import ru.itis.mongo3.dto.ShopDto;
import ru.itis.mongo3.model.Product;
import ru.itis.mongo3.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class SearchController {
    private ProductRepository productRepository;

    @Autowired
    public SearchController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDto>> search(@QuerydslPredicate(root = Product.class) Predicate predicate) {
        return ResponseEntity.ok(StreamSupport.stream(productRepository.findAll(predicate).spliterator(), true)
                .map(product -> ProductDto.builder()
                        .id(product.get_id())
                        .name(product.getName())
                        .category(product.getCategory())
                        .description(product.getDescription())
                        .comments(product.getComments().stream().map(comment -> CommentDto.builder()
                                .text(comment.getText())
                                .build()).collect(Collectors.toList()))
                        .shop(ShopDto.builder()
                                .id(product.getShop().get_id())
                                .name(product.getShop().getName())
                                .logo(product.getShop().getLogo())
                                .build())
                        .build()).collect(Collectors.toList()));
    }
}
