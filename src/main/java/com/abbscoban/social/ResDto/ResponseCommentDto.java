package com.abbscoban.social.ResDto;

import com.abbscoban.social.ReqDto.RequestPostDto;
import com.abbscoban.social.ReqDto.RequestUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentDto {

    private Long Id;

    private ResponsePostDto post;

    private ResponseUserDto user;

    private String text;

    private Date createTime;
}
