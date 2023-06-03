package com.ricecha.s3.service;

import com.google.cloud.translate.v3beta1.*;
import com.google.protobuf.ByteString;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
	private String projectId = "docstranslator-388315";
    private String sourceLanguageCode = "en-US";
    private String targetLanguageCode = "ko";

    @Value("${application.bucket.name}")
    private String s3BucketName;
    
    @Autowired
    private AmazonS3 s3Client;
    
    /**
     * 문서 번역을 수행하는 메서드
     *
     * @param filePath 번역할 문서의 파일 경로
     * @throws Exception 예외 발생 시
     */
    public void translateDocument(String filePath) throws Exception {
    	String translatedObjectName = "translated/" + filePath;
    	
    	try {
    		// Download the file from S3
    		S3Object s3Object = s3Client.getObject(new GetObjectRequest(s3BucketName, filePath));
    		
    		// Get the metadata from the original S3 object
    		ObjectMetadata originalMetadata = s3Object.getObjectMetadata();

    		// Create a new ObjectMetadata for the translated document
    		ObjectMetadata translatedMetadata = new ObjectMetadata();
    		translatedMetadata.setContentType(originalMetadata.getContentType());
    		
    		Path tempFilePath = Files.createTempFile("temp", null);
    		Files.copy(s3Object.getObjectContent(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

    		byte[] inputFileContent = Files.readAllBytes(tempFilePath);
    		
    		// Instantiates a Google Translation client
    		try (TranslationServiceClient translationClient = TranslationServiceClient.create()) {
    			// Set the location and local input file path
    			String location = "global";
    			DocumentInputConfig documentInputConfig = DocumentInputConfig.newBuilder()
    					.setContent(ByteString.copyFrom(inputFileContent))
    					.setMimeType(originalMetadata.getContentType())
    					.build();

    			// Construct request
    			String parent = LocationName.of(projectId, location).toString();
    			TranslateDocumentRequest request = TranslateDocumentRequest.newBuilder()
    					.setParent(parent)
    					.setDocumentInputConfig(documentInputConfig)
    					.setSourceLanguageCode(sourceLanguageCode)
    					.setTargetLanguageCode(targetLanguageCode)
    					.build();

    			// Run request
    			TranslateDocumentResponse response = translationClient.translateDocument(request);

    			// Get the translated document content
    			ByteString translatedContent = response.getDocumentTranslation().getByteStreamOutputs(0);
            
    			// Upload the translated document to S3
    			try (InputStream inputStream = new ByteArrayInputStream(translatedContent.toByteArray())) { // Set the appropriate content type here
    				s3Client.putObject(s3BucketName, translatedObjectName, inputStream, translatedMetadata);
    			}

    			System.out.println("Translated document uploaded to S3.");
    		} catch (Exception e) {
    			e.printStackTrace();
    			//System.out.println("Translation failed: " + e.getMessage());
    		}

    	} catch (Exception e) {
    		e.printStackTrace();
    		//System.out.println("File download failed: " + e.getMessage());
    	}
    }
}
