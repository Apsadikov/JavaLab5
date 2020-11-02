package ru.itis.rabbitmq2.util;

import ru.itis.rabbitmq2.dto.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private PrintWriter printWriter;

    public Logger() {
        File file = new File(Const.LOG_STORAGE);
        try {
            printWriter = new PrintWriter(new FileOutputStream(file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void log(Student student, String actionName) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        printWriter.write("Date: " + formatter.format(date) +
                "; Document: " + actionName +
                "; Full name: " + student.getFirstName() + " " + student.getLastName() +
                "; Course: " + student.getCourse() +
                "; Institute: " + student.getInstituteName() + "\n"
        );
        printWriter.flush();
    }

    public void close() {
        if (printWriter != null) {
            printWriter.flush();
            printWriter.close();
        }
    }
}
