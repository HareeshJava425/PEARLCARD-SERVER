package com.demo.PearlCard.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class APIResponse<T> {

    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("message") 
    private String message;
    
    @JsonProperty("timestamp")
    private String timestamp; // Changed to String for ISO format
    
    @JsonProperty("data")
    private T data;
    
    @JsonProperty("errors")
    private List<String> errors;
    
    public APIResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }
    
    public APIResponse(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    // Static factory methods
    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>(true, "Request processed successfully", data);
    }
    
    public static <T> APIResponse<T> success(String message, T data) {
        return new APIResponse<>(true, message, data);
    }
    
    public static <T> APIResponse<T> error(String message) {
        APIResponse<T> response = new APIResponse<>(false, message, null);
        return response;
    }
    
    public static <T> APIResponse<T> error(String message, List<String> errors) {
        APIResponse<T> response = new APIResponse<>(false, message, null);
        response.setErrors(errors);
        return response;
    }

}