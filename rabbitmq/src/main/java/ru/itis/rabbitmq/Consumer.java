package ru.itis.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static final String EXCHANGE_NAME = "documents";
    private static final String EXCHANGE_TYPE = "fanout";
    private static final String DOCUMENT_TYPE_DISMISSAL = "Dismissal";
    private static final String DOCUMENT_TYPE_EXPULSION = "Expulsion";
    private static final String DOCUMENT_TYPE_ACADEMIC_VACATION = "Academic_vacation";

    public static void main(String[] args) {
        int type = new Scanner(System.in).nextInt();
        String documentType = DOCUMENT_TYPE_ACADEMIC_VACATION;
        if (type == 1) {
            documentType = DOCUMENT_TYPE_DISMISSAL;
        } else if (type == 2) {
            documentType = DOCUMENT_TYPE_EXPULSION;
        }

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, EXCHANGE_NAME, "");

            String finalDocumentType = documentType;
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    PersonInfo personInfo = objectMapper.readValue(new String(message.getBody()), PersonInfo.class);
                    createDocument(finalDocumentType, personInfo);
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (JsonProcessingException | DocumentException e) {
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void addMetaData(Document document, String type) {
        document.addTitle(type);
        document.addSubject(type);
    }

    private static void addContent(Document document, PersonInfo personInfo, String type) throws DocumentException {
        Anchor anchor = new Anchor(type);

        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph();
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Document type: " + type));
        subCatPart.add(new Paragraph("Personal info: "));
        subCatPart.add(new Paragraph("First name: " + personInfo.getFirstName()));
        subCatPart.add(new Paragraph("Last name: " + personInfo.getLastName()));
        subCatPart.add(new Paragraph("Passport number: " + personInfo.getPassportNumber()));
        subCatPart.add(new Paragraph("Data: " + personInfo.getDate()));
        subCatPart.add(new Paragraph("Age: " + personInfo.getAge()));
        document.add(catPart);
    }

    private static void createDocument(String type, PersonInfo personInfo) throws IOException, DocumentException {
        Document document = new Document();
        String fileName = "src/documents/" + type + "_" +
                personInfo.getFirstName() + "_" + personInfo.getLastName() + "_" +
                personInfo.getPassportNumber()
                + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        addMetaData(document, type);
        addContent(document, personInfo, type);
        document.close();
    }
}
