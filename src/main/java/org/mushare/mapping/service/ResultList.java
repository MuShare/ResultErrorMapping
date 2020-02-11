package org.mushare.mapping.service;

import java.util.List;

public class ResultList<T> extends Result<List<T>> {

    private ResultList(ResultCode code, List<T> data) {
        super(code, data);
    }

    public static ResultList success() {
        return new ResultList(CommonResultCode.Success, null);
    }

    public static ResultList error(ResultCode code) {
        return new ResultList(code, null);
    }

    public static <T> ResultList data(List<T> data) {
        return new ResultList<T>(CommonResultCode.Success, data);
    }

}
