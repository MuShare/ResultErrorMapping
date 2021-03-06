package org.mushare.mapping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Response {

    private Map<String, Object> result;
    private HttpStatus status;
    private int error = 0;
    private String message = "";

    private Response(HttpStatus status) {
        this.status = status;
    }

    public static Response badRequest(ErrorCode errorCode) {
        Response response = new Response(HttpStatus.BAD_REQUEST);
        response.error = errorCode.code();
        response.message = errorCode.message();
        return response;
    }

    public static Response ok() {
        Response response = new Response(HttpStatus.OK);
        response.result = new HashMap<>();
        return response;
    }

    public static Response ok(Map<String, Object> result) {
        Response response = new Response(HttpStatus.OK);
        response.result = result;
        return response;
    }

    public static Response success() {
        return Response.ok().append("success", true);
    }

    public Response append(String key, Object value) {
        if (status != HttpStatus.OK) {
            String message = "The result data can only be appended to a response object with OK HttpStatus.";
            String method = Thread.currentThread().getStackTrace()[2].toString();
            System.err.println(new Date() + " " + method + ": " + message);
            return this;
        }
        result.put(key, value);
        return this;
    }

    public Response append(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(object, Map.class);
        map.keySet().forEach(key -> {
            result.put(key, map.get(key));
        });
        return this;
    }

    public ResponseEntity responseEntity() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", status.value());
        data.put("timestamp", System.currentTimeMillis());
        switch (status) {
            case OK:
                data.put("result", result);
                return ResponseEntity.status(status).body(data);
            case BAD_REQUEST:
                data.put("error", error);
                data.put("message", message);
                return ResponseEntity.status(status).body(data);
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
