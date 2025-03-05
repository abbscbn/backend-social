package com.abbscoban.social.Service;

import com.abbscoban.social.Repository.CommentRepository;
import com.abbscoban.social.Repository.PostRepository;
import com.abbscoban.social.Repository.UserRepository;
import com.abbscoban.social.ReqDto.RequestCommentDto;
import com.abbscoban.social.ReqDto.RequestUpdateCommentDto;
import com.abbscoban.social.ResDto.ResponseCommentDto;
import com.abbscoban.social.ResDto.ResponsePostDto;
import com.abbscoban.social.ResDto.ResponseUserDto;
import com.abbscoban.social.exception.BaseExcepiton;
import com.abbscoban.social.exception.ErrorMassage;
import com.abbscoban.social.exception.MassageType;
import com.abbscoban.social.model.Comment;
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
public class CommentService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;



    public Comment createComment(RequestCommentDto requestCommentDto){
        Comment comment= new Comment();
        Optional<User> userOpt = userRepository.findById(requestCommentDto.getUserId());
        Optional<Post> postOpt = postRepository.findById(requestCommentDto.getPostId());

        if(userOpt.isEmpty() || postOpt.isEmpty()){
           throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,requestCommentDto.getUserId().toString()+" "+requestCommentDto.getPostId().toString()));
        }
        comment.setUser(userOpt.get());
        comment.setPost(postOpt.get());
        comment.setText(requestCommentDto.getText());
        comment.setCreateTime(new Date());

        return comment;

    }

    public ResponseCommentDto saveComment(RequestCommentDto requestCommentDto){

        ResponseCommentDto responseCommentDto= new ResponseCommentDto();
        ResponseUserDto responseUserDto= new ResponseUserDto();
        ResponsePostDto responsePostDto=new ResponsePostDto();

        Comment savedComment = commentRepository.save(createComment(requestCommentDto));
        BeanUtils.copyProperties(savedComment.getUser(),responseUserDto);
        BeanUtils.copyProperties(savedComment.getPost(),responsePostDto);
        responsePostDto.setUser(responseUserDto);
        BeanUtils.copyProperties(savedComment,responseCommentDto);
        responseCommentDto.setUser(responseUserDto);
        responseCommentDto.setPost(responsePostDto);
        return responseCommentDto;


    }

    public List<ResponseCommentDto> getCommentsByUserIdOrPostId(Optional<Long> userId, Optional<Long> postId){
        List<ResponseCommentDto> responseCommentDtoList= new ArrayList<>();

        if(userId.isPresent()){
            Optional<User> userOpt = userRepository.findById(userId.get());

            if(userOpt.isEmpty()){
                throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,userId.toString()));
            }


            List<Comment> CommentsByUserId = commentRepository.findByUserId(userId.get());

            if(CommentsByUserId.isEmpty()){
                return responseCommentDtoList;
            }
            for(Comment comment: CommentsByUserId){

                ResponseCommentDto responseCommentDto= new ResponseCommentDto();
                ResponseUserDto responseUserDto= new ResponseUserDto();
                ResponsePostDto responsePostDto= new ResponsePostDto();

                BeanUtils.copyProperties(comment.getUser(),responseUserDto);
                BeanUtils.copyProperties(comment.getPost(),responsePostDto);
                responsePostDto.setUser(responseUserDto);

                BeanUtils.copyProperties(comment,responseCommentDto);

                responseCommentDto.setUser(responseUserDto);
                responseCommentDto.setPost(responsePostDto);

                responseCommentDtoList.add(responseCommentDto);

            }
            return responseCommentDtoList;

        }

        if(postId.isPresent()){
            Optional<Post> postOpt = postRepository.findById(postId.get());
            if(postOpt.isEmpty()){
               throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,postId.toString()));
            }
            List<Comment> CommentsByPostId = commentRepository.findByPostId(postId.get());
            if(CommentsByPostId.isEmpty()){
                return responseCommentDtoList;
            }

            for(Comment comment: CommentsByPostId){

                ResponseCommentDto responseCommentDto= new ResponseCommentDto();
                ResponseUserDto responseUserDto= new ResponseUserDto();
                ResponsePostDto responsePostDto= new ResponsePostDto();

                BeanUtils.copyProperties(comment.getUser(),responseUserDto);
                BeanUtils.copyProperties(comment.getPost(),responsePostDto);

                responsePostDto.setUser(responseUserDto);

                BeanUtils.copyProperties(comment,responseCommentDto);

                responseCommentDto.setUser(responseUserDto);
                responseCommentDto.setPost(responsePostDto);

                responseCommentDtoList.add(responseCommentDto);

            }

            return responseCommentDtoList;

        }

        List<Comment> allComments = commentRepository.findAll();

        if(allComments.isEmpty()){
            return new ArrayList<ResponseCommentDto>();
        }

        for(Comment comment: allComments){

            ResponseCommentDto responseCommentDto= new ResponseCommentDto();
            ResponseUserDto responseUserDto= new ResponseUserDto();
            ResponsePostDto responsePostDto= new ResponsePostDto();

            BeanUtils.copyProperties(comment.getUser(),responseUserDto);
            BeanUtils.copyProperties(comment.getPost(),responsePostDto);

            responsePostDto.setUser(responseUserDto);

            BeanUtils.copyProperties(comment,responseCommentDto);

            responseCommentDto.setUser(responseUserDto);
            responseCommentDto.setPost(responsePostDto);

            responseCommentDtoList.add(responseCommentDto);

        }

        return responseCommentDtoList;

    }

    public ResponseCommentDto getCommentByCommentId(Long commentId){

        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if(commentOpt.isEmpty()){
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,commentId.toString()));
        }
        Comment comment=commentOpt.get();

        ResponseCommentDto responseCommentDto= new ResponseCommentDto();
        ResponseUserDto responseUserDto= new ResponseUserDto();
        ResponsePostDto responsePostDto= new ResponsePostDto();

        BeanUtils.copyProperties(comment.getUser(),responseUserDto);
        BeanUtils.copyProperties(comment.getPost(),responsePostDto);
        responsePostDto.setUser(responseUserDto);
        BeanUtils.copyProperties(comment,responseCommentDto);

        responseCommentDto.setUser(responseUserDto);
        responseCommentDto.setPost(responsePostDto);

        return responseCommentDto;

    }

    public ResponseCommentDto updateComment(Long commentId, RequestUpdateCommentDto requestUpdateCommentDto){
        ResponseCommentDto responseCommentDto= new ResponseCommentDto();
        ResponseUserDto responseUserDto=new ResponseUserDto();
        ResponsePostDto responsePostDto= new ResponsePostDto();
        Comment toUpdate= new Comment();
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if(commentOpt.isEmpty()){
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,commentId.toString()));

        }
        toUpdate=commentOpt.get();
        BeanUtils.copyProperties(requestUpdateCommentDto,toUpdate);
        Comment UpdatedComment = commentRepository.save(toUpdate);

        BeanUtils.copyProperties(UpdatedComment.getUser(),responseUserDto);
        BeanUtils.copyProperties(UpdatedComment.getPost(),responsePostDto);
        responsePostDto.setUser(responseUserDto);
        BeanUtils.copyProperties(UpdatedComment,responseCommentDto);
        responseCommentDto.setUser(responseUserDto);
        responseCommentDto.setPost(responsePostDto);

        return responseCommentDto;


    }
    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }
}
