package com.ricecha.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import com.ricecha.s3.service.StorageService;
import com.ricecha.s3.service.TranslationService;

@SpringBootTest
class FileTrashServiceApplicationTests {

	@Autowired
    private StorageService service;
	
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
