package ru.itis.rabbitmq2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.rabbitmq2.util.Const;
import ru.itis.rabbitmq2.dto.Photo;

import java.io.IOException;

@Controller
public class PhotoController {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public PhotoController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/photo")
    public String send() {
        return "photo";
    }

    @PostMapping("/photo")
    public String send(@RequestParam("photo") MultipartFile photo, @RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName) {
        try {
            rabbitTemplate.setExchange(Const.FANOUT_EXCHANGE_NAME);
            rabbitTemplate.convertAndSend(objectMapper.writeValueAsString(Photo.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .photoByte(photo.getBytes())
                    .build()).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/photo";
    }
}
