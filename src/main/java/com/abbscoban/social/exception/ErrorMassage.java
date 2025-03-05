package com.abbscoban.social.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMassage {

    private MassageType massageType;

    private String ofStatic;

    public String prepareErrorMassage(){
        StringBuilder builder=new StringBuilder();
        builder.append(massageType.getMassage());
        if(ofStatic!=null){
            builder.append(" : "+ofStatic);
        }

        return builder.toString();

    }
}
