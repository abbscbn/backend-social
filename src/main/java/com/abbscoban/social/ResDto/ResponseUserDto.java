package com.abbscoban.social.ResDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {

    private Long Id;

    private String username;

    private String password;

    private Date createTime;

    private String profilePicture;

}
