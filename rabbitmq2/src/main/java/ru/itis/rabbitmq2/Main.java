package ru.itis.rabbitmq2;

import ru.itis.rabbitmq2.consumer.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        new ExpulsionDocumentConsumer().start();
        new VacationDocumentConsumer().start();
        new DocumentLoggingConsumer().start();
        new PhotoOriginalConsumer().start();
        new PhotoSquareConsumer().start();
    }
}
