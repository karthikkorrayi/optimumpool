package com.OptimumPool.Authentication.Model;

import org.springframework.stereotype.Component;
@Component
public class MQToken {
    private String mqtoken;

    public String getMqtoken() {
        return mqtoken;
    }

    public void setMqtoken(String mqtoken) {
        this.mqtoken = mqtoken;
    }

    public MQToken(String mqtoken) {
        this.mqtoken = mqtoken;
    }

    public MQToken() {
        super();
    }
}
