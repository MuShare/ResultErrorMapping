package org.mushare.mapping.service;

import org.mushare.mapping.mapping.ResultErrorMapping;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class Result<T> {

    private ResultCode code;
    private T data = null;

    public T getData() {
        return data;
    }

    public ResultCode getCode() {
        return code;
    }

    protected Result(ResultCode code, T data) {
        this.code = code;
        this.data = data;
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
        return new ResultErrorMapping(code);
    }

    public ResponseEntity responseEntity(Function<ResultErrorMapping, ResultErrorMapping> mapError,
                                   Function<T, ResponseEntity> generateResponseEntity) {
        if (hasError()) {
            ResultErrorMapping errorMapping = errorMapping();
            return mapError.apply(errorMapping).responseEntity();
        }
        return generateResponseEntity.apply(getData());
    }

}