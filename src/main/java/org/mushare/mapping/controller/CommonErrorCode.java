package org.mushare.mapping.controller;

public enum CommonErrorCode implements ErrorCode {

    Unknown(800, "Unknown error."),
    AuthToken(801, "Auth token error."),
    NoPrivilge(802, "No privilege to invoke this method."),
    ObjecId(803, "Object cannot be found by the object id."),
    SavingObject(804, "Internal error saving object.");

    public int code;
    public String message;

    private CommonErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
