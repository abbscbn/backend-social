package com.abbscoban.social.Service;

import com.abbscoban.social.Repository.UserRepository;
import com.abbscoban.social.ReqDto.RequestUserDto;
import com.abbscoban.social.ResDto.ResponseUserDto;
import com.abbscoban.social.exception.BaseExcepiton;
import com.abbscoban.social.exception.ErrorMassage;
import com.abbscoban.social.exception.MassageType;
import com.abbscoban.social.model.User;
import org.apache.catalina.valves.HealthCheckValve;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseUserDto saveNewUser(RequestUserDto requestUserDto){
        User user= new User();
        ResponseUserDto responseUserDto= new ResponseUserDto();
        user.setCreateTime(new Date());
        BeanUtils.copyProperties(requestUserDto,user);
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser,responseUserDto);
        return responseUserDto;
    }

    public ResponseUserDto getUserById(Long UserId){
        ResponseUserDto responseUserDto= new ResponseUserDto();

        Optional<User> findedUserOpt = userRepository.findById(UserId);

         if(findedUserOpt.isPresent()){
            BeanUtils.copyProperties(findedUserOpt.get(),responseUserDto);
            return responseUserDto;
         }

         throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,UserId.toString()));

    }
    public List<ResponseUserDto> getAllUsers(){
        List<User> allUser = userRepository.findAll();
        List<ResponseUserDto> responseUserDtoList= new ArrayList<>();
        for(User user: allUser){
            ResponseUserDto responseUserDto= new ResponseUserDto();
            BeanUtils.copyProperties(user,responseUserDto);
            responseUserDtoList.add(responseUserDto);

        }
        return responseUserDtoList;
    }
    public ResponseUserDto updateUserById(Long userId,RequestUserDto requestUserDto){
        Optional<User> findedUserOpt = userRepository.findById(userId);
        ResponseUserDto responseUserDto= new ResponseUserDto();
        if(findedUserOpt.isPresent()){
            User findUser= findedUserOpt.get();
            findUser.setUsername(requestUserDto.getUsername());
            findUser.setPassword(requestUserDto.getUsername());
            User updatedUser = userRepository.save(findUser);
            BeanUtils.copyProperties(updatedUser,responseUserDto);
            return responseUserDto;
        }
        else {
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,userId.toString()));
        }
    }

    public void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    public ResponseUserDto checkUserByUserNameAndPassword(RequestUserDto requestUserDto){
        ResponseUserDto responseUserDto=new ResponseUserDto();
        Optional<User> byUsernameAndPassword = userRepository.findByUsernameAndPassword(requestUserDto.getUsername(), requestUserDto.getPassword());
        if(byUsernameAndPassword.isPresent()){
           BeanUtils.copyProperties(byUsernameAndPassword.get(),responseUserDto);
           return responseUserDto;
        }
        else {
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,requestUserDto.getUsername()+" "+ requestUserDto.getPassword()));
        }
    }

    public String getPicByUserId(Long userId){
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()){
            throw new BaseExcepiton(new ErrorMassage(MassageType.NO_RECORD_EXIST,userId.toString()));
        }
        return userOpt.get().getProfilePicture();

    }

}
