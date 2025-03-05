package com.abbscoban.social.Controller;

import com.abbscoban.social.ReqDto.RequestPostDto;
import com.abbscoban.social.ReqDto.RequestUpdatePostDto;
import com.abbscoban.social.ResDto.ResponsePostDto;
import com.abbscoban.social.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping
    public List<ResponsePostDto> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    @PostMapping("/save")
    public ResponsePostDto createPost(@RequestBody RequestPostDto requestPostDto){

        return postService.savePost(requestPostDto);

    }
    @GetMapping("/{postId}")
    public ResponsePostDto getPostByPostId(@PathVariable Long postId){
        return postService.getPostByPostId(postId);
    }

    @PutMapping("/{postId}")
    public  ResponsePostDto updatePostByPostId(@PathVariable Long postId,@RequestBody RequestUpdatePostDto requestUpdatePostDto){
        return postService.updatePostByPostId(postId,requestUpdatePostDto);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }

    @GetMapping("/users")
    public List<ResponsePostDto> getPostsByUserId(@RequestParam(name = "userId",required = true) Long userId){
        return postService.getPostsByUserId(userId);
    }

}
