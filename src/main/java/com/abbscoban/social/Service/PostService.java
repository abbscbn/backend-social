package com.abbscoban.social.Service;

import com.abbscoban.social.Repository.PostRepository;
import com.abbscoban.social.Repository.UserRepository;
import com.abbscoban.social.ReqDto.RequestPostDto;
import com.abbscoban.social.ReqDto.RequestUpdatePostDto;
import com.abbscoban.social.ResDto.ResponsePostDto;
import com.abbscoban.social.ResDto.ResponseUserDto;
import com.abbscoban.social.exception.BaseExcepiton;
import com.abbscoban.social.exception.ErrorMassage;
import com.abbscoban.social.exception.MassageType;
import com.abbscoban.social.model.Post;
import com.abbscoban.social.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;


    public List<ResponsePostDto> getAllPosts(Optional<Long> userId){

        List<ResponsePostDto> ResponsePostDtoList= new ArrayList<>();

        if(userId.isPresent()){
            List<Post> ListByUserId = postRepository.findAllByUserIdOrderByCreateTimeDesc(userId.get());
            if(ListByUserId.isEmpty()){
                return  new ArrayList<ResponsePostDto>();
            }
            for(Post post : ListByUserId ){
                ResponseUserDto responseUserDto= new ResponseUserDto();
                ResponsePostDto responsePostDto= new ResponsePostDto();
                BeanUtils.copyProperties(post,responsePostDto);
                BeanUtils.copyProperties(post.getUser(),responseUserDto);
                responsePostDto.setUser(responseUserDto);
                ResponsePostDtoList.add(responsePostDto);
            }
            return ResponsePostDtoList;
        }
        List<Post> allList = postRepository.findAllByOrderByCreateTimeDesc();
        if(allList.isEmpty())
        {
            return  new ArrayList<ResponsePostDto>();
        }
        for (Post post: allList){
            ResponseUserDto responseUserDto= new ResponseUserDto();
            ResponsePostDto responsePostDto= new ResponsePostDto();
            BeanUtils.copyProperties(post,responsePostDto);
            BeanUtils.copyProperties(post.getUser(),responseUserDto);
            responsePostDto.setUser(responseUserDto);
            ResponsePostDtoList.add(responsePostDto);
        }
        return ResponsePostDtoList;
    }

    public Post createPost(RequestPostDto requestPostDto){

        Optional<User> userOpt = userRepository.findById(requestPostDto.getUserId());
        ResponsePostDto responsePostDto= new ResponsePostDto();
        if(userOpt.isEmpty()){
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,requestPostDto.getUserId().toString()));
        }
        Post post= new Post();
        post.setUser(userOpt.get());
        BeanUtils.copyProperties(requestPostDto,post);
        post.setCreateTime(new Date());
        return post;

    }

    public ResponsePostDto savePost(RequestPostDto requestPostDto){
        ResponsePostDto responsePostDto= new ResponsePostDto();
        ResponseUserDto responseUserDto= new ResponseUserDto();

        Post savedPost = postRepository.save(createPost(requestPostDto));

        BeanUtils.copyProperties(savedPost,responsePostDto);
        BeanUtils.copyProperties(savedPost.getUser(),responseUserDto);

        responsePostDto.setUser(responseUserDto);

        return responsePostDto;


    }

    public ResponsePostDto getPostByPostId(Long postId){
        ResponseUserDto responseUserDto= new ResponseUserDto();
        ResponsePostDto responsePostDto= new ResponsePostDto();
        Optional<Post> postOpt = postRepository.findById(postId);
        if(postOpt.isEmpty()){
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,postId.toString()));
        }
        BeanUtils.copyProperties(postOpt.get().getUser(),responseUserDto);
        BeanUtils.copyProperties(postOpt.get(),responsePostDto);
        responsePostDto.setUser(responseUserDto);
        return responsePostDto;
    }

    public ResponsePostDto updatePostByPostId(Long postId, RequestUpdatePostDto requestUpdatePostDto){
        ResponsePostDto responsePostDto= new ResponsePostDto();
        ResponseUserDto responseUserDto= new ResponseUserDto();
        Post toUpdate= new Post();
        Optional<Post> postOpt = postRepository.findById(postId);
        if(postOpt.isEmpty()){
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,postId.toString()));
        }
        toUpdate=postOpt.get();
        BeanUtils.copyProperties(requestUpdatePostDto,toUpdate);
        Post updatedPost = postRepository.save(toUpdate);
        BeanUtils.copyProperties(updatedPost.getUser(),responseUserDto);
        BeanUtils.copyProperties(updatedPost,responsePostDto);
        responsePostDto.setUser(responseUserDto);
        return responsePostDto;

    }
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }

    public List<ResponsePostDto> getPostsByUserId(Long userId){

        List<ResponsePostDto> responsePostDtoList= new ArrayList<>();

        List<Post> PostList = postRepository.findByUserId(userId);

        if(PostList.isEmpty()){
            return new ArrayList<ResponsePostDto>();
        }

        for(Post post: PostList){

            ResponsePostDto responsePostDto=  new ResponsePostDto();
            ResponseUserDto responseUserDto= new ResponseUserDto();
            BeanUtils.copyProperties(post,responsePostDto);
            BeanUtils.copyProperties(post.getUser(),responseUserDto);
            responsePostDto.setUser(responseUserDto);

            responsePostDtoList.add(responsePostDto);

        }
        return responsePostDtoList;

    }
}
