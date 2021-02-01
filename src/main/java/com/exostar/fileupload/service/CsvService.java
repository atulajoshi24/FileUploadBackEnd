package com.exostar.fileupload.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvService {

	public void saveFile(MultipartFile file);
	
}
