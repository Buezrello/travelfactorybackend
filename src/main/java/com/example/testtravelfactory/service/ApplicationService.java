package com.example.testtravelfactory.service;

import com.example.testtravelfactory.dao.ApplicationRepository;
import com.example.testtravelfactory.entity.Application;
import com.example.testtravelfactory.entity.Translation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ExcelService excelService;
    private final JsonService jsonService;

    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> findApplicationById(long id) {
        return applicationRepository.findById(id);
    }

    public Application saveApplication(Application application) {
        if (application.getTranslations() != null) {
            for (Translation translation : application.getTranslations()) {
                translation.setApplication(application);
            }
        }
        return applicationRepository.save(application);
    }

    public void downloadTranslations(Long id, HttpServletResponse response) {
        try {
            Optional<Application> applicationOptional = applicationRepository.findById(id);
            if (applicationOptional.isPresent()) {
                Application application = applicationOptional.get();
                ByteArrayOutputStream outputStream = excelService.generateExcel(application);

                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=translations.xlsx");
                response.getOutputStream().write(outputStream.toByteArray());
                response.flushBuffer();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Application not found");
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deployTranslations(Long id) {
        Optional<Application> applicationOptional = applicationRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Application application = applicationOptional.get();
            try {
                jsonService.saveTranslationsAsJson(application);
                return ResponseEntity.ok("Deployment successful");
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Error during deployment: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(404).body("Application not found");
        }
    }

}

