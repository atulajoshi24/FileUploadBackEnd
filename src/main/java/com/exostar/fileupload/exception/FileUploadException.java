package com.exostar.fileupload.exception;

public class FileUploadException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 815149476357633614L;
	
	public FileUploadException(String errorMessage,Throwable error) {
		// TODO Auto-generated constructor stub
		super(errorMessage, error);
	}
	
	public FileUploadException(String errorMessage) {
		// TODO Auto-generated constructor stub
		super(errorMessage);
	}
	
}
