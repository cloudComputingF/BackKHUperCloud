package com.ricecha.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ricecha.s3.service.StorageService;
import com.ricecha.s3.service.TranslationService;

@SpringBootTest
class FileTrashServiceApplicationTests {
	
	@Autowired
    private StorageService service;
	
	@Autowired
	TranslationService translationService;
	
	@Test
	void testTranslateAndExportToS3()
	{
        String filePath = "Main/slide.pdf"; // Replace with the path to your file
        
        try {
            translationService.translateDocument(filePath);
            System.out.println("Translation and export to S3 completed successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred during translation and export to S3: " + e.getMessage());
        }
	}
	/*
	@Test
    void deleteFileTest() {
        String uploadFilePath = "trash/a.docx";
        String result = service.deleteFile(uploadFilePath);
        System.out.println("Delete File Result: " + result);
    }
	
    @Test
    void restoreFileTest() {
        String uploadFilePath = "trash/a.txt";
        String result = service.restoreFile(uploadFilePath);
        System.out.println("Restore File Result: " + result);
    }
    */
}
