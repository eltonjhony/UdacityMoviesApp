package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.infrastructure.MyLog;

import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

import static com.helabs.eltonjhony.udacitymovies.MyApplication.getApplication;
import static com.helabs.eltonjhony.udacitymovies.data.remote.ErrorHandler.Error.GENERIC_CODE;
import static com.helabs.eltonjhony.udacitymovies.data.remote.ErrorHandler.Error.GENERIC_MESSAGE;

/**
 * Created by eltonjhony on 4/3/17.
 */
public class ErrorHandler {

    private Throwable t;

    public ErrorHandler(Throwable t) {
        this.t = t;
    }

    public Error extract() {
        MyLog.error(ErrorHandler.class.getSimpleName(), t.getMessage());
        if (t instanceof HttpException) {
            ResponseBody body = ((HttpException) t).response().errorBody();
            try {
                Gson gson = new Gson();
                Error error = gson.fromJson(body.string(), Error.class);
                MyLog.error(error.code, error.message);
                return new Error(error.getErrorCode(), error.getErrorMessage());
            } catch (Exception e) {
                return new Error(GENERIC_CODE, GENERIC_MESSAGE);
            }
        }
        return new Error(GENERIC_CODE, GENERIC_MESSAGE);
    }

    public static class Error implements Serializable {

        static final String GENERIC_MESSAGE = getApplication().getString(R.string.generic_error_message);
        static final int GENERIC_CODE = 1;

        @SerializedName("status_code")
        public int code;

        @SerializedName("status_message")
        public String message;

        @SerializedName("errors")
        private List<String> errors;

        public Error() {
        }

        public Error(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public Error(List<String> errors) {
            this.errors = errors;
        }

        public int getErrorCode() {
            return this.code;
        }

        public String getErrorMessage() {
            if (this.errors != null && !this.errors.isEmpty()) {
                return this.errors.get(0);
            }
            return this.message;
        }
    }

}
