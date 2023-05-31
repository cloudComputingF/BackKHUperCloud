package com.ricecha.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.ricecha.s3.service.StorageService;
import com.ricecha.s3.service.TranslationService;

@Configuration
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class FileTrashServiceApplicationTests {

	@Autowired
    private StorageService service;
	
	 @TestConfiguration
	 @ComponentScan(basePackages = "com.ricecha.s3.service")
	 static class TestConfig {
	     // You can define any additional beans required for testing here
	 }

	 @Bean
	 TranslationService translationService() {
	     return new TranslationService();
	 }
	
	void dtestTranslateAndExportToS3()
	{
		ConfigurableApplicationContext context = SpringApplication.run(FileTrashServiceApplicationTests.class);

        TranslationService translationService = context.getBean(TranslationService.class);

        String filePath = "path_to_your_file";
        translationService.translateAndExportToS3(filePath);

        context.close();
	}
	
	@Test
    void deleteFileTest() {
        String uploadFilePath = "test";
        String uuidFileName = "simple-pod.yaml";
        String result = service.deleteFile(uploadFilePath, uuidFileName);
        System.out.println("Delete File Result: " + result);
    }

    @Test
    void restoreFileTest() {
        String uploadFilePath = "test";
        String uuidFileName = "simple-pod.yaml";
        String destinationFilePath = "restored";
        String newFileName = "simple-pod-restored.yaml";
        String result = service.restoreFile(uploadFilePath, uuidFileName, destinationFilePath, newFileName);
        System.out.println("Restore File Result: " + result);
    }

}
