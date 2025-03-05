package com.abbscoban.social.Controller;

import com.abbscoban.social.ReqDto.RequestLikeDto;
import com.abbscoban.social.Service.LikeService;
import com.abbscoban.social.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/{postId}")
    public Integer getLikeCountByPostId(@PathVariable Long postId){
        return likeService.getLikeCountByPostId(postId);
    }

    @GetMapping
    public Boolean checkLikedPostByUserId(@RequestParam (name = "postId" ,required = true) Long postId,
                                        @RequestParam (name = "userId", required = true) Long userId){
        return likeService.checkLikedPostByUserId(postId,userId);
    }

    @PostMapping
    public void saveLike(@RequestBody RequestLikeDto requestLikeDto){
         likeService.saveLike(requestLikeDto);
    }

    @DeleteMapping
    public void removeLikeByUserIdAndPostId(@RequestParam (name = "postId" ,required = true) Long postId,
                                            @RequestParam (name = "userId", required = true) Long userId){
        likeService.removeLikeByUserIdAndPostId(userId,postId);
    }

}
