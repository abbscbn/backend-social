package com.abbscoban.social.Service;

import com.abbscoban.social.Repository.LikeRepository;
import com.abbscoban.social.Repository.PostRepository;
import com.abbscoban.social.Repository.UserRepository;
import com.abbscoban.social.ReqDto.RequestLikeDto;
import com.abbscoban.social.model.Like;
import com.abbscoban.social.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;


    public Integer getLikeCountByPostId(Long postId){

        List<Like> LikeList = likeRepository.findByPostId(postId);
        return LikeList.size();
    }

    public Boolean checkLikedPostByUserId(Long postId, Long userId){

        Like byPostIdAndUserId = likeRepository.findByPostIdAndUserId(postId, userId);

        if(byPostIdAndUserId != null){
            return true;
        }
        else{
            return false;

        }
    }

    public void saveLike(RequestLikeDto requestLikeDto){

         Like newLike= new Like();

         newLike.setUser(userRepository.findById(requestLikeDto.getUserId()).get());
         newLike.setPost(postRepository.findById(requestLikeDto.getPostId()).get());

         likeRepository.save(newLike);

    }
    public void removeLikeByUserIdAndPostId(Long userId, Long postId){
        Like byPostIdAndUserId = likeRepository.findByPostIdAndUserId(postId, userId);
        Long LikeId = byPostIdAndUserId.getId();
        likeRepository.deleteById(LikeId);

    }

}
