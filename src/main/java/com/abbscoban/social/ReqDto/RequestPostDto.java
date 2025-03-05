package com.abbscoban.social.ReqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostDto {

    private Long userId;

    private String title;

    private String text;

}
