package org.mushare.mapping.service;

import org.mushare.mapping.controller.Response;
import org.mushare.mapping.mapping.ErrorMapping;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class Result<T> {

    private ResultCode code;
    private T data = null;
    private ErrorMapping errorMapping;

    protected Result(ResultCode code, T data) {
        this.code = code;
        this.data = data;
        this.errorMapping = new ErrorMapping(code);
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

    private boolean errorEquals(ResultCode code) {
        return this.code.equals(code);
    }

    private boolean hasError() {
        return !code.equals(CommonResultCode.Success);
    }

    public Result<T> mapError(Function<ErrorMapping, ErrorMapping> mapError) {
        errorMapping = mapError.apply(errorMapping);
        return this;
    }

    public ResponseEntity successResponseEntity() {
        if (hasError()) {
            return errorMapping.responseEntity();
        }
        return Response.success().responseEntity();
    }

    public ResponseEntity responseEntity(Function<T, Response> generateResponse) {
        if (hasError()) {
            return errorMapping.responseEntity();
        }
        return generateResponse.apply(data).responseEntity();
    }

}