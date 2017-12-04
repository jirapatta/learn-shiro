package com.hd.learnshiro.exception;

public class NotFoundException extends Exception {

    public NotFoundException(String id) {
        super(id);
    }
}