package ru.itis.rabbitmq2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.rabbitmq2.dto.Student;
import ru.itis.rabbitmq2.util.Const;

@Controller
public class ExpulsionDocumentController {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public ExpulsionDocumentController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/expulsion")
    public String get() {
        return "expulsion";
    }

    @PostMapping("/expulsion")
    public String post(@RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName,
                       @RequestParam("course") int course, @RequestParam("institute") String institute) {
        try {
            String json = objectMapper.writeValueAsString(Student.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .course(course)
                    .instituteName(institute)
                    .build());
            rabbitTemplate.setExchange(Const.DIRECT_EXCHANGE_NAME);
            rabbitTemplate.convertAndSend(Const.ROUTING_KEY_EXPULSION, json);
            rabbitTemplate.setExchange(Const.TOPIC_EXCHANGE_NAME);
            rabbitTemplate.convertAndSend(Const.ROUTING_KEY_EXPULSION, json);
            return "redirect:/expulsion";
        } catch (JsonProcessingException e) {
            return "redirect:/expulsion?error";
        }
    }
}
