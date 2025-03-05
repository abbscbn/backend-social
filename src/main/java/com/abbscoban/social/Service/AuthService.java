package com.abbscoban.social.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.abbscoban.social.ResDto.ResponseUserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;


import com.abbscoban.social.jwt.AuthRequest;
import com.abbscoban.social.jwt.AuthResponse;
import com.abbscoban.social.jwt.JwtService;
import com.abbscoban.social.model.RefleshToken;
import com.abbscoban.social.model.User;
import com.abbscoban.social.Repository.RefleshTokenRepository;
import com.abbscoban.social.Repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefleshTokenRepository refleshTokenRepository;

    private RefleshToken createRefleshToken(User user) {
        RefleshToken refleshToken= new RefleshToken();
        refleshToken.setRefleshToken(UUID.randomUUID().toString());
        refleshToken.setExpireDate(new Date(System.currentTimeMillis()+1000*60*60*4));
        refleshToken.setUser(user);
        return refleshToken;

    }


    public AuthResponse authenticate(AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken auth=
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            authenticationProvider.authenticate(auth);
            Optional<User> optionalUser= userRepository.findByUsername(request.getUsername());
            String accessToken = jwtService.generateToken(optionalUser.get());
            RefleshToken refleshToken = createRefleshToken(optionalUser.get());

            refleshTokenRepository.save(refleshToken);

            return new AuthResponse(accessToken,refleshToken.getRefleshToken(),optionalUser.get().getUsername(),optionalUser.get().getCreateTime(),optionalUser.get().getId());


        } catch (Exception e) {
            System.out.println("Kullanıcı adı veya şifre hatalı");
        }
        return null;
    }



    public ResponseUserDto register(AuthRequest request) {

        User user= new User();
        user.setCreateTime(new Date());
        ResponseUserDto dtoUser= new ResponseUserDto();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        BeanUtils.copyProperties(user, dtoUser);

        return dtoUser;
    }
    

}
