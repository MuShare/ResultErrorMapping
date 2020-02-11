package org.mushare.mapping.service;

public enum CommonResultCode implements ResultCode {

    Success(901),
    AccessTokenError(902),
    NoPrivilege(903),
    SaveInternalError(904);

    public int code;

    CommonResultCode(int code) {
        this.code = code;
    }

    @Override
    public int code() {
        return code;
    }

}
