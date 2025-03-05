package com.abbscoban.social.ReqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLikeDto {

    private Long userId;

    private Long postId;

}
