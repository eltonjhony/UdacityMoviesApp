package com.helabs.eltonjhony.udacitymovies.data.exceptions;

/**
 * Created by eltonjhony on 31/08/17.
 */

public class ApiException extends Exception {

    private String message;

    public ApiException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
