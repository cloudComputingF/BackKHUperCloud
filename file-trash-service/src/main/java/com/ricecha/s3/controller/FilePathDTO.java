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
    
    public String getFilePath() {
    	return filePath;
    }
    
    public void setFilePath(String filePath) {
    	this.filePath = filePath;
    }
}
