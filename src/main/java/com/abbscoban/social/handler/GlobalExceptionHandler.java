package com.abbscoban.social.handler;

import com.abbscoban.social.exception.BaseExcepiton;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(value = {BaseExcepiton.class})
    public ResponseEntity<ApiError> handlerException(BaseExcepiton excepiton, WebRequest request){
       return ResponseEntity.badRequest().body(createApiError(excepiton.getMessage(),request));
    }

    public <E> ApiError<E> createApiError(E massage, WebRequest request){
        ApiError<E> apiError=new ApiError<>();
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        Exception<E> exception=new Exception<>();
        exception.setCreateTime(new Date());
        exception.setHostName(getHostName());
        exception.setPath(request.getDescription(false).substring(4));
        exception.setMassage(massage);

        apiError.setException(exception);

        return apiError;
    }

    public String getHostName(){
        try {
         return    InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("Local Host alınırken bir hata oluştu: "+e.getMessage());
        }
        return null;
    }

}
