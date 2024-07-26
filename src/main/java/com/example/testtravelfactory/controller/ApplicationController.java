package com.example.testtravelfactory.controller;

import com.example.testtravelfactory.entity.Application;
import com.example.testtravelfactory.service.ApplicationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.findAllApplications();
    }

    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.saveApplication(application);
    }

    @GetMapping("/download/{id}")
    public void downloadTranslations(@PathVariable Long id, HttpServletResponse response) {
        applicationService.downloadTranslations(id, response);
    }

    @PostMapping("/deploy/{id}")
    public ResponseEntity<String> deployTranslations(@PathVariable Long id) {
        return applicationService.deployTranslations(id);
    }
}
