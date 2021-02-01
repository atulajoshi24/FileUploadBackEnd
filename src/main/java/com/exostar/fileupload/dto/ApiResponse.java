package com.exostar.fileupload.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ApiResponse {

    private boolean success;
    private List<?> data;
    List<ApiError> errors = new ArrayList<>();
    
}
