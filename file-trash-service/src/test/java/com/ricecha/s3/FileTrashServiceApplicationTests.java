package com.ricecha.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ricecha.s3.service.StorageService;

@SpringBootTest
class FileTrashServiceApplicationTests {

	@Autowired
    private StorageService service;
	
	@Test
    void deleteFileTest() {
        String uploadFilePath = "test1";
        String uuidFileName = "hw12_공태현_2019102144.py";
        String result = service.deleteFile(uploadFilePath, uuidFileName);
        System.out.println("Delete File Result: " + result);
    }

    @Test
    void restoreFileTest() {
        String uploadFilePath = "test1";
        String uuidFileName = "hw12_공태현_2019102144.py";
        String destinationFilePath = "restored";
        String newFileName = "hw12_공태현_2019102144_restore.py";
        String result = service.restoreFile(uploadFilePath, uuidFileName, destinationFilePath, newFileName);
        System.out.println("Restore File Result: " + result);
    }

}
