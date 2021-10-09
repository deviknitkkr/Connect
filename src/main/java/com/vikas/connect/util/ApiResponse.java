package com.vikas.connect.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse<T> {

    private String timestamp;
    private Integer code;
    private String description;
    private String message;
    private T response;

    public static <U extends Object> ApiResponse<U> from(U obj, HttpStatus httpStatus) {

        ApiResponse<U> apiResponse = new ApiResponse<>();
        apiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setCode(httpStatus.value());
        apiResponse.setDescription(httpStatus.name());

        if (httpStatus.isError())
            apiResponse.setMessage(obj == null ? null : obj.toString());
        else
            apiResponse.setResponse(obj);

        return apiResponse;
    }

    public static <U extends Object> ApiResponse<U> from(U obj, String error, HttpStatus httpStatus) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        apiResponse.setCode(httpStatus.value());
        apiResponse.setDescription(httpStatus.name());
        apiResponse.setMessage(error);

        return apiResponse;
    }

}
