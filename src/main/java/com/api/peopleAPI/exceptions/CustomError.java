package com.api.peopleAPI.exceptions;

import java.util.Date;

public class CustomError {
    private Date date;
    private String message;
    private String urlRequest;

    public CustomError(Date date, String message, String urlRequest) {
        this.date = date;
        this.message = message;
        this.urlRequest = urlRequest;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlRequest() {
        return urlRequest;
    }

    public void setUrlRequest(String urlRequest) {
        this.urlRequest = urlRequest;
    }
}
