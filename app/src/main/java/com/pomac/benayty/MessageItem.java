package com.pomac.benayty;

public class MessageItem {

    private String message;

    private String imagePath;

    private String userName;

    private String userPhone;

    public MessageItem(String message, String imagePath, String userName, String userPhone) {
        this.message = message;
        this.imagePath = imagePath;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
