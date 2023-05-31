package com.ricecha.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

	@Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String deleteFile(String uploadFilePath, String uuidFileName) {

	    String result = "success";

	    try {
	      String keyName = uploadFilePath + "/" + uuidFileName; // ex) 구분/년/월/일/파일.확장자
	      boolean isObjectExist = s3Client.doesObjectExist(bucketName, keyName);
	      if (isObjectExist) {
	        s3Client.deleteObject(bucketName, keyName);
	      } else {
	        result = "file not found";
	      }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

	    return result;
	  }

    public String restoreFile(String uploadFilePath, String uuidFileName, String destinationFilePath, String newFileName) {
	    String result = "success";
	    String sourceKey = uploadFilePath + "/" + uuidFileName;
	    String destinationKey = destinationFilePath + "/" + newFileName;
	    
	    try {
	      boolean isSourceObjectExist = s3Client.doesObjectExist(bucketName, sourceKey);
	      if (isSourceObjectExist) {
	        // 파일 복사 요청 생성
	        CopyObjectRequest copyRequest = new CopyObjectRequest(bucketName, sourceKey, bucketName, destinationKey);

	        // 파일 복사 실행
	        s3Client.copyObject(copyRequest);

	        // 원본 파일 삭제 요청 생성
	        DeleteObjectRequest deleteRequest = new DeleteObjectRequest(bucketName, sourceKey);

	        // 원본 파일 삭제 실행
	        s3Client.deleteObject(deleteRequest);
	      } else {
	        result = "source file not found";
	      }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	result = "failed";
	    }

	    return result;
	  }

}
