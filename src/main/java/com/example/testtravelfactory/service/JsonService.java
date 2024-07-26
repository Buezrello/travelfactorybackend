package com.example.testtravelfactory.service;

import com.example.testtravelfactory.entity.Application;
import com.example.testtravelfactory.entity.Translation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
@Setter
public class JsonService {
    @Value("${translation.json.directory}")
    private String jsonDirectory;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveTranslationsAsJson(Application application) throws IOException {
        Map<String, String> translations = new HashMap<>();
        for (Translation translation : application.getTranslations()) {
            translations.put(translation.getTranslationKey(), translation.getValue());
        }

        File directory = Paths.get(jsonDirectory).toAbsolutePath().toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File jsonFile = new File(directory + File.separator + application.getName() + ".json");
        objectMapper.writeValue(jsonFile, translations);
    }
}
