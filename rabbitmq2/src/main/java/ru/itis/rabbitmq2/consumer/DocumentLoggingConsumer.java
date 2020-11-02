package ru.itis.rabbitmq2.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import ru.itis.rabbitmq2.dto.Student;
import ru.itis.rabbitmq2.util.Const;
import ru.itis.rabbitmq2.util.Logger;

public class DocumentLoggingConsumer extends BaseConsumer {
    private Logger logger;

    public DocumentLoggingConsumer() {
        super(Const.TOPIC_EXCHANGE_NAME, "topic", "document.#");
        logger = new Logger();
    }

    @Override
    protected DeliverCallback getDeliveryCallback() {
        return (consumerTag, message) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(message.getBody(), Student.class);
            logger.log(student, message.getEnvelope().getRoutingKey());
            getChannel().basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
    }
}
