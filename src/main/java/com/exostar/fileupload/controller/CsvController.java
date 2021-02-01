package com.exostar.fileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exostar.fileupload.dto.ApiResponse;
import com.exostar.fileupload.service.CsvService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping(value = "api/csv")
@Slf4j
public class CsvController {

	private CsvService csvService;
	
	@Autowired
	public CsvController(CsvService csvService) {
		this.csvService = csvService;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file) throws InterruptedException {
		
		log.debug("Entering CsvController.uploadFile");
		this.csvService.saveFile(file);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setSuccess(true);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);	
	}
	   	
	@GetMapping("/test")
	public ResponseEntity<ApiResponse> test() {
			
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setSuccess(true);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);	
	} 
	
}
