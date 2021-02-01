package com.exostar.fileupload.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exostar.fileupload.domain.User;
import com.exostar.fileupload.exception.FileUploadException;
import com.exostar.fileupload.repository.UserJpaRepository;
import com.exostar.fileupload.util.CsvHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CsvServiceImpl implements CsvService{
	
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	public CsvServiceImpl(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}
	

	@Override
	public void saveFile(MultipartFile file) {
		// TODO Auto-generated method stub	
		log.debug("Entering CsvServiceImpl.saveFile");
		try {	
			if(CsvHelper.hasCSVFormat(file)) {					
				List<User> users = CsvHelper.csvToUsers(file.getInputStream());
				log.info("Total Number Of Users getting saved = "+users.size());
				this.userJpaRepository.saveAll(users);					
			}else {
				log.error("CsvServiceImpl.saveFile wrong file format ");
				throw new FileUploadException("Wrong File Format "+file.getContentType());
			}
		}catch(IOException iox) {
			log.error("Error In CsvServiceImpl.saveFile",iox);
			throw new FileUploadException("Error Reading CSV File ",iox);
		}
		
	}

}
