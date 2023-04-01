package com.ymp.restfulwebservice.model;

import lombok.Data;

@Data
// using lombok to get getter and setter method which is needed to inject data from properties file into fields by setter method, getter method is need for access field value from other class.
public class ResponseMessage {

    public ResponseMessage(String message, String contactMe) {
        this.message = message;
        ContactMe = contactMe;
    }

    private String message;
    private String ContactMe;
}
