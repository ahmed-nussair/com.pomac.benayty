package com.pomac.benayty.model;

import com.google.gson.annotations.SerializedName;

public class FcmSendResult {

    @SerializedName("message_id")
    private String messageId;

    private String error;

    public String getMessageId() {
        return messageId;
    }

    public String getError() {
        return error;
    }
}
