package ru.itis.rabbitmq2.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import ru.itis.rabbitmq2.dto.Student;
import ru.itis.rabbitmq2.util.Const;
import ru.itis.rabbitmq2.util.ExpulsionDocumentCreator;

public class ExpulsionDocumentConsumer extends BaseConsumer {

    public ExpulsionDocumentConsumer() {
        super(Const.DIRECT_EXCHANGE_NAME, "direct", Const.ROUTING_KEY_EXPULSION);
    }

    @Override
    protected DeliverCallback getDeliveryCallback() {
        return (consumerTag, message) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(message.getBody(), Student.class);
            new ExpulsionDocumentCreator(student).createDocument();
            getChannel().basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
    }
}
