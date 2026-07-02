package com.example;

public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "Hello from Dependency Injection!";
    }
}
