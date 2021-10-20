package com.wareroom.lib_http.exception;

import android.text.TextUtils;

public class ApiException extends Exception {
    public static final String TAG = "ApiException";
    private String code;
    private String displayMessage;

    public ApiException(String code, String displayMessage) {
        super(displayMessage);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String displayMessage) {
        super(displayMessage);
        this.code = String.valueOf(code);
        this.displayMessage = displayMessage;
    }

    public ApiException(String code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        if (TextUtils.isEmpty(displayMessage)) {
            return getMessage();
        }
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
