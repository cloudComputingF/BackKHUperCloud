package com.ricecha.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.ricecha.s3.service.TranslationService;

@SpringBootApplication
public class FileTrashServiceApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FileTrashServiceApplication.class, args);

        TranslationService translationService = context.getBean(TranslationService.class);

        String filePath = "path_to_your_file";
        translationService.translateAndExportToS3(filePath);

        context.close();
    }

}
