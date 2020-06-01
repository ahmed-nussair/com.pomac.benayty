package com.pomac.benayty.model.response;

public class AddingCommentResponse {

    private int status;

    private String message;

    private String[] errors;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String[] getErrors() {
        return errors;
    }
}
