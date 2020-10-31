package ru.itis.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    private static final String EXCHANGE_NAME = "documents";
    private static final String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            boolean isCanceled = false;
            Scanner scanner = new Scanner(System.in);
            while (!isCanceled) {
                String text = scanner.nextLine();
                if (text.equals("/exit")) {
                    isCanceled = true;
                } else if (text.equals("/enter")) {
                    PersonInfo personInfo = new PersonInfo();
                    System.out.println("Имя");
                    String firstName = scanner.nextLine();
                    personInfo.setFirstName(firstName);

                    System.out.println("Фамилия");
                    String lastName = scanner.nextLine();
                    personInfo.setLastName(lastName);

                    System.out.println("Номер паспорта");
                    String passportNumber = scanner.nextLine();
                    personInfo.setPassportNumber(passportNumber);

                    System.out.println("Дата выдачи");
                    String data = scanner.nextLine();
                    personInfo.setDate(data);

                    System.out.println("Возраст");
                    int age = scanner.nextInt();
                    personInfo.setAge(age);
                    ObjectMapper mapper = new ObjectMapper();
                    channel.basicPublish(EXCHANGE_NAME, "", null, mapper.writeValueAsString(personInfo).getBytes());
                }
            }
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
