package com.ricecha.s3.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePathDTO {
	private String filePath;
	public FilePathDTO()
	{
	
	}
    public FilePathDTO(String filePath) {
    	this.filePath = filePath;
    }
    
    public String GetFilePathDTO() {
    	return filePath;
    }
    
    public void SetFilePathDTO(String filePath) {
    	this.filePath = filePath;
    }
}
