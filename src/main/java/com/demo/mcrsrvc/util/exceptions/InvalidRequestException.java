package com.demo.mcrsrvc.util.exceptions;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String msg){
        super(msg);
    }

}
