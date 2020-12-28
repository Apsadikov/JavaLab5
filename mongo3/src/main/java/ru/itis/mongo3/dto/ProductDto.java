package ru.itis.mongo3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.mongo3.model.Comment;
import ru.itis.mongo3.model.Shop;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto {
    private String id;
    private String name;
    private String category;
    private String description;
    private Double price;
    private List<CommentDto> comments;
    private ShopDto shop;
}
