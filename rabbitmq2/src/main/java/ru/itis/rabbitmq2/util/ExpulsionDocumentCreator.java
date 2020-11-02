package ru.itis.rabbitmq2.util;

import com.itextpdf.forms.PdfAcroForm;
import ru.itis.rabbitmq2.dto.Student;

import java.util.UUID;

public class ExpulsionDocumentCreator extends DocumentCreator {
    private Student student;

    public ExpulsionDocumentCreator(Student student) {
        this.student = student;
    }

    @Override
    protected void fillDocument() {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, true);
        form.getField("full_name").setReadOnly(true);
        form.getField("full_name").setValue(student.getFirstName() + " " + student.getLastName());

        form.getField("course").setReadOnly(true);
        form.getField("course").setValue(String.valueOf(student.getCourse()));

        form.getField("institute").setReadOnly(true);
        form.getField("institute").setValue(student.getInstituteName());
    }

    @Override
    public String getTemplateName() {
        return "expulsion";
    }

    @Override
    public String getGeneratedFileName() {
        return student.getFirstName() + "_" + student.getLastName() + "_" + UUID.randomUUID().toString();
    }
}
