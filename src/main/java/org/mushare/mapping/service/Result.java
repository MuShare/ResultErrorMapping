package org.mushare.mapping.service;

import org.mushare.mapping.controller.Response;
import org.mushare.mapping.mapping.ResultErrorMapping;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class Result<T> {

    private ResultCode code;
    private T data = null;
    private ResultErrorMapping errorMapping;

    public T getData() {
        return data;
    }

    public ResultCode getCode() {
        return code;
    }

    protected Result(ResultCode code, T data) {
        this.code = code;
        this.data = data;
        this.errorMapping = new ResultErrorMapping(code);
    }

    public static Result success() {
        return new Result(CommonResultCode.Success, null);
    }

    public static Result error(ResultCode code) {
        return new Result(code, null);
    }

    public static <T> Result data(T data) {
        return new Result<T>(CommonResultCode.Success, data);
    }

    public boolean errorEquals(ResultCode code) {
        return this.code.equals(code);
    }

    public boolean hasError() {
        return !code.equals(CommonResultCode.Success);
    }

    public ResultErrorMapping errorMapping() {
        return errorMapping;
    }

    public Result<T> mapError(Function<ResultErrorMapping, ResultErrorMapping> mapError) {
        errorMapping = mapError.apply(errorMapping);
        return this;
    }

    public ResponseEntity successResponseEntity() {
        if (hasError()) {
            return errorMapping.responseEntity();
        }
        return Response.success().build();
    }

    public ResponseEntity responseEntity(Function<T, Response> generateResponse) {
        if (hasError()) {
            return errorMapping.responseEntity();
        }
        return generateResponse.apply(data).build();
    }

}