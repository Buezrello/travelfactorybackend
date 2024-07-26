package com.example.testtravelfactory;

import com.example.testtravelfactory.entity.Application;
import com.example.testtravelfactory.entity.Translation;
import com.example.testtravelfactory.service.JsonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TestTravelFactoryApplicationTests {

	@TempDir
	Path tempDir;

	@Autowired
	private JsonService jsonService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testSaveTranslationsAsJson() throws IOException {
		Application application = new Application();
		application.setId(1L);
		application.setName("App1");
		application.setTranslations(Arrays.asList(
				new Translation(1L, "greeting", "Hello", application),
				new Translation(2L, "farewell", "Goodbye", application)
		));

		// Use the temp directory for testing
		jsonService.setJsonDirectory(tempDir.toString());

		jsonService.saveTranslationsAsJson(application);
		File jsonFile = new File(tempDir.toString(), "App1.json");

		// Verify that the file was created
		assertTrue(jsonFile.exists());

		// Verify that the file contains the correct data
		Map<String, String> translations = objectMapper.readValue(jsonFile, Map.class);
		assertEquals(2, translations.size());
		assertEquals("Hello", translations.get("greeting"));
		assertEquals("Goodbye", translations.get("farewell"));
	}


}
