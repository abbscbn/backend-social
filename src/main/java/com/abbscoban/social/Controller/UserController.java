package com.abbscoban.social.Controller;

import com.abbscoban.social.ReqDto.RequestUserDto;
import com.abbscoban.social.ResDto.ResponseUserDto;
import com.abbscoban.social.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseUserDto saveNewUser(@RequestBody RequestUserDto requestUserDto){

        return userService.saveNewUser(requestUserDto);
    }

    @GetMapping("/{userId}")
    public ResponseUserDto getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<ResponseUserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public ResponseUserDto updateUserById(@PathVariable Long userId, @RequestBody RequestUserDto requestUserDto){
        return userService.updateUserById(userId,requestUserDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
    }


    @PostMapping("/login")
    public ResponseUserDto checkUserByUserNameAndPassword(@RequestBody RequestUserDto requestUserDto){

        return userService.checkUserByUserNameAndPassword(requestUserDto);
    }

    @GetMapping("/getpic")
    public String getPicByUserId(@RequestParam(name = "userId", required = true) Long userId ){
        return  userService.getPicByUserId(userId);
    }

}
