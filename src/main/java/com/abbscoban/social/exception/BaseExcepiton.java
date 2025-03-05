package com.abbscoban.social.exception;

public class BaseExcepiton extends RuntimeException{

    public BaseExcepiton(){

    }
    public BaseExcepiton(ErrorMassage errorMassage){
    super(errorMassage.prepareErrorMassage());
    }
}
