package com.ricecha.s3.controller;

import com.ricecha.s3.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trash")
public class FileController {

	@Autowired
    private StorageService service;

    @DeleteMapping("/delete/{uploadFilePath}/{uuidFileName}")
    public ResponseEntity<String> deleteFile(
  	      @RequestParam(value = "uploadFilePath") String uploadFilePath,
  	      @RequestParam(value = "uuidFileName") String uuidFileName) {
  	    
  	  String result = service.deleteFile(uploadFilePath, uuidFileName);
  	  if (result.equals("success")) {
  	      return ResponseEntity.status(HttpStatus.OK).body("File deleted successfully.");
  	    } else {
  	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file.");
  	    }
  	  }
    
    @DeleteMapping("/restore/{uploadFilePath}/{uuidFileName}/{destinationFilePath}")
	  public ResponseEntity<Object> restoreFile(
	      @RequestParam(value = "uploadFilePath") String uploadFilePath,
	      @RequestParam(value = "uuidFileName") String uuidFileName,
	      @RequestParam(value = "destinationFilePath") String destinationFilePath,
	      @RequestParam(value = "newFileName", required = false) String newFileName) {
	    // 파일을 다른 위치로 이동하는 기능 호출하여 파일 이동 작업 수행
	    String result;
	    if (newFileName != null) {
	      result = service.restoreFile(uploadFilePath, uuidFileName, destinationFilePath, newFileName);
	    } else {
	      result = service.restoreFile(uploadFilePath, uuidFileName, destinationFilePath, null);
	    }

	    if (result.equals("success")) {
	      return ResponseEntity.status(HttpStatus.OK).body("File moved successfully.");
	    } else if (result.equals("source file not found")) {
	      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Source file not found.");
	    } else {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to move file.");
	    }
	  }
    
}
