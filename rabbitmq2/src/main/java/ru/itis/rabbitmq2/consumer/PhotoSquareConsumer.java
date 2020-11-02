package ru.itis.rabbitmq2.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import ru.itis.rabbitmq2.dto.Photo;
import ru.itis.rabbitmq2.util.Const;
import ru.itis.rabbitmq2.util.SquarePhotoHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoSquareConsumer extends BaseConsumer {
    public PhotoSquareConsumer() {
        super(Const.FANOUT_EXCHANGE_NAME, "fanout", "");
    }

    @Override
    protected DeliverCallback getDeliveryCallback() {
        return (consumerTag, message) -> {
            try {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Photo photo = objectMapper.readValue(message.getBody(), Photo.class);
                    String path = Const.PHOTO_STORAGE + "square_" + photo.getFirstName() + "_" + photo.getLastName() + ".jpg";
                    File file = new File(path);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(photo.getPhotoByte());
                    fileOutputStream.close();
                    SquarePhotoHelper.makeSquarePhoto(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getChannel().basicAck(message.getEnvelope().getDeliveryTag(), false);
            } catch (JsonProcessingException e) {
                getChannel().basicReject(message.getEnvelope().getDeliveryTag(), false);
            }
        };
    }
}
