package com.ricecha.s3.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ricecha.s3.service.TranslationService;
import java.io.File;

public class TranslationController {
	
	private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping("/document")
    public ResponseEntity<String> translateDocument(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String filePath = saveFileLocally(file);
            translationService.translateAndExportToS3(filePath);
            return ResponseEntity.ok("Translation completed and exported to S3");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during translation: " + e.getMessage());
        }
    }

    private String saveFileLocally(MultipartFile file) throws IOException {
        String filePath = "path_to_save_file_locally"; // Update with the desired local file path
        File localFile = new File(filePath);
        file.transferTo(localFile);
        return filePath;
    }
}
