package ru.itis.rabbitmq2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student {
    private String firstName;
    private String lastName;
    private String instituteName;
    private int course;
}
