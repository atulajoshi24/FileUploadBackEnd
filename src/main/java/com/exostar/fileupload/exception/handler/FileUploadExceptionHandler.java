package com.exostar.fileupload.exception.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.exostar.fileupload.dto.ApiError;
import com.exostar.fileupload.dto.ApiResponse;
import com.exostar.fileupload.exception.FileUploadException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class FileUploadExceptionHandler extends ResponseEntityExceptionHandler{

	
		@ExceptionHandler(TransactionSystemException.class)
		public ResponseEntity<ApiResponse> handleConstraintViolation(Exception ex, WebRequest request) {
			log.info("entering TransactionSystemException Exception Handler");
		    Throwable cause = ((TransactionSystemException) ex).getRootCause();
		    ApiResponse apiResponse = new ApiResponse();
		    apiResponse.setSuccess(false);
		    if (cause instanceof ConstraintViolationException) {	
		    	  log.info("Handling ConstraintViolationException ");
				  ConstraintViolationException exception = (ConstraintViolationException)cause;
				  List<ApiError> apiErrors = exception.getConstraintViolations().stream().map(constraint -> new ApiError("",constraint.getMessage())).
						  collect(Collectors.toList());
				  apiResponse.setErrors(apiErrors);				  
				  return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		    }	   
		    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	
	
		@ExceptionHandler(FileUploadException.class)
		public ResponseEntity<ApiResponse> handleFileUploadException(FileUploadException ex, WebRequest request) {
			
			log.info("entering FileUploadException Exception Handler");
			ApiError error = createApiErrorMessage(ex,HttpStatus.INTERNAL_SERVER_ERROR);
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setSuccess(false);
			apiResponse.setErrors(Arrays.asList(error));
		    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		    
		}
		
		@ExceptionHandler(Exception.class)
		public ResponseEntity<ApiResponse> handleGlobalException(Exception ex, WebRequest request) {
			
			log.info("entering Global Exception Handler ");
			ApiError error = createApiErrorMessage(ex,HttpStatus.INTERNAL_SERVER_ERROR);
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setSuccess(false);
			apiResponse.setErrors(Arrays.asList(error));
		    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		    
		}
		
		
		private ApiError createApiErrorMessage(Exception ex,HttpStatus status) {
			
			ApiError apiError = new ApiError();
			apiError.setCode(status.toString());
			apiError.setMessage(ex.getMessage());
			return apiError;
		}
		
		
	
}
