package com.exostar.fileupload.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.exostar.fileupload.domain.User;
import com.exostar.fileupload.util.FileUploadConstants.CsvFileHeaders;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CsvHelper {
	
	  private static String TYPE = "text/csv";

	  public static boolean hasCSVFormat(MultipartFile file) {
		log.info("CsvHelper.hasCSVFormat "+file.getContentType());
	    if (TYPE.equals(file.getContentType())
	    		|| file.getContentType().equals("application/vnd.ms-excel")) {
	      return true;
	    }

	    return false;
	  }

	  public static List<User> csvToUsers(InputStream is) {
		  
		log.debug("Entering CsvHelper.csvToUsers");
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withHeader(CsvFileHeaders.class).withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

	      List<CSVRecord> csvRecords = csvParser.getRecords();
	      List<User> users = null;
	      if(csvRecords != null) {
	    	 log.info("Total Number of CSV Records = "+csvRecords.size());
	    	 users = csvRecords.stream()
	    			  .peek(csvRecord -> log.info("Processing CSV Record "+csvRecord))
	    			  .map(csvRecord -> new User(Long.valueOf(csvRecord.get(CsvFileHeaders.Id)),csvRecord.get(CsvFileHeaders.FirstName),
	    			  csvRecord.get(CsvFileHeaders.LastName),csvRecord.get(CsvFileHeaders.Email))).collect(Collectors.toList());	    	  
	      }else {
	    	  users = Collections.emptyList();
	      }
	      return users;
	    } catch (IOException iox) {
	    	log.error("Error in CsvHelper.csvToUsers ",iox);
	        throw new RuntimeException("Fail to parse CSV file: " +iox.getMessage());
	    }
	  }

}
