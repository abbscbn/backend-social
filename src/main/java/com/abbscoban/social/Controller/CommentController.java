package com.abbscoban.social.Controller;

import com.abbscoban.social.ReqDto.RequestCommentDto;
import com.abbscoban.social.ReqDto.RequestUpdateCommentDto;
import com.abbscoban.social.ResDto.ResponseCommentDto;
import com.abbscoban.social.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public ResponseCommentDto saveComment(@RequestBody RequestCommentDto requestCommentDto){
        return commentService.saveComment(requestCommentDto);
    }

    @GetMapping
    public List<ResponseCommentDto> getCommentsByUserIdOrPostId(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getCommentsByUserIdOrPostId(userId,postId);
    }

    @GetMapping("/{commentId}")
    public ResponseCommentDto getCommentByCommentId(@PathVariable Long commentId){

      return commentService.getCommentByCommentId(commentId);

    }

    @PutMapping("{commentId}")
    public ResponseCommentDto updateComment(@PathVariable Long commentId, @RequestBody RequestUpdateCommentDto requestUpdateCommentDto){
        return commentService.updateComment(commentId,requestUpdateCommentDto);
    }

    @DeleteMapping("{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }

}
