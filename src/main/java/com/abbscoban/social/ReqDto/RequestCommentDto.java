package com.abbscoban.social.ReqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommentDto {

    private Long postId;

    private Long userId;

    private String text;
}
