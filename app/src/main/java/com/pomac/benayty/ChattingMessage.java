package com.pomac.benayty;

public class ChattingMessage {

    private String from;

    private String to;

    private String message;

    private long timeStamp;

    private boolean read;

    public ChattingMessage(String from, String to, String message, long timeStamp, boolean read) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.timeStamp = timeStamp;
        this.read = read;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
