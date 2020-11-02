package ru.itis.rabbitmq2.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SquarePhotoHelper {
    public static void makeSquarePhoto(File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);

        BufferedImage resizedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, 512, 512);

        int tempWidth;
        int tempHeight;
        int y = 0;
        int x = 0;

        if (bufferedImage.getHeight() < bufferedImage.getWidth()) {
            tempWidth = 512;
            tempHeight = (int) (((double) bufferedImage.getHeight() * 512) / bufferedImage.getWidth());
            y = -(tempHeight - tempWidth) / 2;
        } else {
            tempHeight = 512;
            tempWidth = (int) (((double) bufferedImage.getWidth() * 512) / bufferedImage.getHeight());
            x = -(tempWidth - tempHeight) / 2;
        }

        graphics.drawImage(bufferedImage.getScaledInstance(tempWidth, tempHeight, Image.SCALE_SMOOTH), x, y, null);
        graphics.dispose();

        ImageIO.write(resizedImage, "jpg", file);
        bufferedImage.flush();
    }
}
