package com.ricecha.s3.controller;

import com.ricecha.s3.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trash")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {

	@Autowired
    private StorageService service;

	/**
     * 파일을 삭제하는 엔드포인트
     *
     * @param uploadFilePath 삭제할 파일의 경로
     * @return 응답 엔티티 (성공 시 "File deleted successfully.", 실패 시 "Failed to delete file.")
     */
    @DeleteMapping("delete/{uploadFilePath}")
    public ResponseEntity<String> deleteFile(
  	      @RequestParam(value = "uploadFilePath") String uploadFilePath) {
  	    
  	  String result = service.deleteFile(uploadFilePath);
  	  if (result.equals("success")) {
  	      return ResponseEntity.status(HttpStatus.OK).body("File deleted successfully.");
  	    } else {
  	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file.");
  	    }
  	  }
    
    /**
     * 파일을 복원하는 엔드포인트
     *
     * @param uploadFilePath 복원할 파일의 경로
     * @return 응답 엔티티 (성공 시 "File moved successfully.", 원본 파일이 없을 시 "Source file not found.", 실패 시 "Failed to move file.")
     */
    @DeleteMapping("restore/{uploadFilePath}")
	  public ResponseEntity<Object> restoreFile(
	      @RequestParam(value = "uploadFilePath") String uploadFilePath) {
	    // 파일을 다른 위치로 이동하는 기능 호출하여 파일 이동 작업 수행
	    String result;
	    result = service.restoreFile(uploadFilePath);

	    if (result.equals("success")) {
	      return ResponseEntity.status(HttpStatus.OK).body("File moved successfully.");
	    } else if (result.equals("source file not found")) {
	      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Source file not found.");
	    } else {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to move file.");
	    }
	  }
    
}
