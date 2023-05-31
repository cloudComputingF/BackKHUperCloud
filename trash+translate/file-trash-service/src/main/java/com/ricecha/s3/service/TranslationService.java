package com.ricecha.s3.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.v3beta1.*;
import com.google.protobuf.ByteString;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class TranslationService {
	private static final String projectId = "docstranslator-388315";
    private static final String location = "global";
    private static final String sourceLanguageCode = "en-US";
    private static final String targetLanguageCode = "sr-Latn";
    private static final String s3BucketName = "khucloud";
    private static final String s3ObjectKey = "translated_file.txt";

    public void translateAndExportToS3(String filePath) {
        try {
            // Google Cloud Translation 인증 정보 설정
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            TranslationServiceSettings translationServiceSettings = TranslationServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            // Google Cloud Translation 클라이언트 생성
            try (TranslationServiceClient translationClient = TranslationServiceClient.create(translationServiceSettings)) {
                // 파일 로드
                File file = new File(filePath);
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                ByteString content = ByteString.copyFrom(fileBytes);

               // 번역 요청 생성
                TranslateTextGlossaryConfig translateTextGlossaryConfig = TranslateTextGlossaryConfig.newBuilder().build();
                TranslateTextRequest translateTextRequest = TranslateTextRequest.newBuilder()
                        .setParent(String.format("projects/%s/locations/%s", projectId, location))
                        .setMimeType("text/plain")
                        .setSourceLanguageCode(sourceLanguageCode)
                        .setTargetLanguageCode(targetLanguageCode)
                        .addContentsBytes(content)
                        .setGlossaryConfig(translateTextGlossaryConfig)
                        .build();

                // 번역 실행
                TranslateTextResponse translateTextResponse = translationClient.translateText(translateTextRequest);

                // 번역 결과 가져오기
                String translatedText = translateTextResponse.getTranslations(0).getTranslatedText();

                // S3에 번역된 파일 업로드
                S3Client s3Client = S3Client.builder()
                        .region(Region.US_EAST_1) // 적절한 리전을 선택
                        .credentialsProvider(DefaultCredentialsProvider.create())
                        .build();
                s3Client.putObject(PutObjectRequest.builder()
                        .bucket(s3BucketName)
                        .key(s3ObjectKey)
                        .build(), RequestBody.fromString(translatedText, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
