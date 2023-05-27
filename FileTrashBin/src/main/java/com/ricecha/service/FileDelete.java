package com.ricecha.service;

import com.amazonaws.services.s3.AmazonS3Client;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileDelete {
	
	@Value("${spring.s3.bucket}")
	private String bucketName;

	private final AmazonS3Client amazonS3Client;
	  
	public String deleteFile(String uploadFilePath, String uuidFileName) {

	    String result = "success";

	    try {
	      String keyName = uploadFilePath + "/" + uuidFileName; // ex) 구분/년/월/일/파일.확장자
	      boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, keyName);
	      if (isObjectExist) {
	        amazonS3Client.deleteObject(bucketName, keyName);
	      } else {
	        result = "file not found";
	      }
	    } catch (Exception e) {
	      log.debug("Delete File failed", e);
	    }

	    return result;
	  }
	
	public String restoreFile(String uploadFilePath, String uuidFileName, String destinationFilePath, String newFileName) {
	    String result = "success";
	    String sourceKey = uploadFilePath + "/" + uuidFileName;
	    String destinationKey = destinationFilePath + "/" + newFileName;
	    
	    try {
	      boolean isSourceObjectExist = amazonS3Client.doesObjectExist(bucketName, sourceKey);
	      if (isSourceObjectExist) {
	        // 파일 복사 요청 생성
	        CopyObjectRequest copyRequest = new CopyObjectRequest(bucketName, sourceKey, bucketName, destinationKey);

	        // 파일 복사 실행
	        amazonS3Client.copyObject(copyRequest);

	        // 원본 파일 삭제 요청 생성
	        DeleteObjectRequest deleteRequest = new DeleteObjectRequest(bucketName, sourceKey);

	        // 원본 파일 삭제 실행
	        amazonS3Client.deleteObject(deleteRequest);
	      } else {
	        result = "source file not found";
	      }
	    } catch (Exception e) {
	      log.debug("File move failed", e);
	      result = "failed";
	    }

	    return result;
	  }
	
	/**
	   * UUID 파일명 반환
	   */
	  public String getUuidFileName(String fileName) {
	    String ext = fileName.substring(fileName.indexOf(".") + 1);
	    return UUID.randomUUID().toString() + "." + ext;
	  }

}
