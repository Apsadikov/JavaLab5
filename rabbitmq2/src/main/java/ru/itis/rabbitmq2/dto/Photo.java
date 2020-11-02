package ru.itis.rabbitmq2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Photo {
    private String firstName;
    private String lastName;
    private byte[] photoByte;
}
