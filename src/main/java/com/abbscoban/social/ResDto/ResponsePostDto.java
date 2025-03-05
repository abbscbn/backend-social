package com.abbscoban.social.ResDto;

import com.abbscoban.social.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponsePostDto {

    private Long Id;

    private ResponseUserDto user;

    private String title;

    private String text;

    private Date createTime;
}
