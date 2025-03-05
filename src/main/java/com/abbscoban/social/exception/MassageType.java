package com.abbscoban.social.exception;


import lombok.Getter;


@Getter
public enum MassageType {

    NO_RECORD_EXIST("1001","KULLANICI BULUNAMADI"),
    GENERAL_EXCEPTION("9999","GENEL BİR HATA OLUŞTU");

    private String code;

    private String massage;

    MassageType(String code, String massage){
        this.code=code;
        this.massage=massage;
    }

}
