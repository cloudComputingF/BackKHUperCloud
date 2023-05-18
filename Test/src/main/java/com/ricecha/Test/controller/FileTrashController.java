package com.ricecha.Test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;

public class FileTrashController {
	
	private static final String TRASH_DIRECTORY = "/path/to/trash/directory/";
	private static final String RESTORED_DIRECTORY = "/path/to/restored/directory/";
	
	@GetMapping("/files")
    public ResponseEntity<List<String>> getFileList() {
        try {
        	File trashDirectory = new File(TRASH_DIRECTORY);
        	File[] files = trashDirectory.listFiles();

        	if (files != null && files.length > 0) {
        		List<String> fileList = new ArrayList<>();
            	for (File file : files) {
            		fileList.add(file.getName());
            	}
            	return ResponseEntity.ok(fileList);
        	} else {
            	return ResponseEntity.ok().body(new ArrayList<>());
        	}
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
	
	@DeleteMapping("/files/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
		try {
			File file = new File(TRASH_DIRECTORY + filename);

			if (file.exists()) {
				if (file.delete()) {
					return ResponseEntity.ok("File deleted successfully");
				} else {
					return ResponseEntity.status(500).body("Failed to delete file");
				}
			} else {
				return ResponseEntity.notFound().build();
			}
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
	
	@PostMapping("/files/{filename}/restore")
    public ResponseEntity<String> restoreFile(@PathVariable String filename) {
        try {
        	File trashFile = new File(TRASH_DIRECTORY + filename);
        	File restoredFile = new File(RESTORED_DIRECTORY + filename);

        	if (trashFile.exists()) {
        		try {
        			Path sourcePath = trashFile.toPath();
        			Path targetPath = restoredFile.toPath();
        			Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        			trashFile.delete();
        			return ResponseEntity.ok("File restored successfully");
        		} catch (Exception e) {
        			return ResponseEntity.status(500).body("Failed to restore file");
        		}
        	} else {
        		return ResponseEntity.notFound().build();
        	}
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
