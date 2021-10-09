package com.vikas.connect.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    public static <T> void sendResponse(HttpServletResponse response, T result,HttpStatus httpStatus) throws IOException {
        ApiResponse<T> response1 = ApiResponse.from(result, httpStatus);
        ObjectMapper objectMapper = new ObjectMapper();

        response.resetBuffer();
        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(objectMapper.writeValueAsString(response1));
        response.flushBuffer();
    }

}
