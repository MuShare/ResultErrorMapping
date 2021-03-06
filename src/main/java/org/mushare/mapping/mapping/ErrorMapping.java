package org.mushare.mapping.mapping;

import org.mushare.mapping.controller.CommonErrorCode;
import org.mushare.mapping.controller.ErrorCode;
import org.mushare.mapping.controller.Response;
import org.mushare.mapping.service.ResultCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorMapping {

    private ResultCode code;
    private Map<ResultCode, ErrorCode> errorMap = new HashMap<>();

    public ErrorMapping(ResultCode code) {
        this.code = code;
    }

    public ErrorMapping append(ResultCode resultCode, ErrorCode errorCode) {
        errorMap.put(resultCode, errorCode);
        return this;
    }

    public ErrorMapping append(Map<ResultCode, ErrorCode> map) {
        errorMap.putAll(map);
        return this;
    }

    public ResponseEntity responseEntity() {
        for (ResultCode resultCode : errorMap.keySet()) {
            if (resultCode.code() == code.code()) {
                return Response.badRequest(errorMap.get(resultCode)).responseEntity();
            }
        }
        return Response.badRequest(CommonErrorCode.Unknown).responseEntity();
    }

}
