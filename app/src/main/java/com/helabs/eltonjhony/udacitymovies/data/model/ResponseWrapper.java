package com.helabs.eltonjhony.udacitymovies.data.model;

/**
 * Created by Bruno Ferrari on 03 March 2017.
 */

public abstract class ResponseWrapper {

    private int httpCode = 0;
    String httpResponseMsg = null;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getHttpResponseMsg() {
        return httpResponseMsg;
    }

    public void setHttpResponseMsg(String httpResponseMsg) {
        this.httpResponseMsg = httpResponseMsg;
    }
}
