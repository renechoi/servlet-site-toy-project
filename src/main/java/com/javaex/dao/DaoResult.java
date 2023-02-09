package com.javaex.dao;

public class DaoResult {
    private String message;

    public DaoResult(String message) {
        this.message = message;
    }

    public DaoResult(int message){
        this.message = parseResult(message);
    }

    private String parseResult(int message) {
        return message == 1 ? "success" : "fail";
    }
}
