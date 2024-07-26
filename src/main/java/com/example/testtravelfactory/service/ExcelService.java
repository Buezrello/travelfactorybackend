package com.example.testtravelfactory.service;

import com.example.testtravelfactory.entity.Application;
import com.example.testtravelfactory.entity.Translation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExcelService {
    public ByteArrayOutputStream generateExcel(Application application) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Translations");

        // Header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Last Deployment");

        // Application data row
        Row appRow = sheet.createRow(1);
        appRow.createCell(0).setCellValue(application.getId());
        appRow.createCell(1).setCellValue(application.getName());
        appRow.createCell(2).setCellValue(application.getLastDeployment());

        // Header row for translations
        Row translationHeaderRow = sheet.createRow(3);
        translationHeaderRow.createCell(0).setCellValue("Translation Key");
        translationHeaderRow.createCell(1).setCellValue("Translation Value");

        // Data rows for translations
        int rowNum = 4;
        for (Translation translation : application.getTranslations()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(translation.getTranslationKey());
            row.createCell(1).setCellValue(translation.getValue());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }

}

