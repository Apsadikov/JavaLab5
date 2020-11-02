package ru.itis.rabbitmq2.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import ru.itis.rabbitmq2.dto.Student;
import ru.itis.rabbitmq2.util.Const;
import ru.itis.rabbitmq2.util.VacationDocumentCreator;

public class VacationDocumentConsumer extends BaseConsumer {
    public VacationDocumentConsumer() {
        super(Const.DIRECT_EXCHANGE_NAME, "direct", Const.ROUTING_KEY_VACATION);
    }

    @Override
    protected DeliverCallback getDeliveryCallback() {
        return (consumerTag, message) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(message.getBody(), Student.class);
            new VacationDocumentCreator(student).createDocument();
            getChannel().basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
    }
}
