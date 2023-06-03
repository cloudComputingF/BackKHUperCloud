package com.ricecha.s3.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

	// AWS S3 액세스 키 가져오기
	@Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

	// AWS S3 시크릿 키 가져오기
    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;
    
    // AWS S3 리전 가져오기
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    AmazonS3 s3Client() {
    	// AWS 자격 증명 생성
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        
        // AmazonS3 클라이언트 빌더를 사용하여 AmazonS3 클라이언트 생성
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }
}
