package com.odeyalo.netflix.emailsenderservice.dto;

public class PhoneNumberSmsDTO {
    private String phoneNumber;
    private String body;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "PhoneNumberSmsDTO{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
