package com.abbscoban.social.Service;

import java.nio.channels.Pipe.SourceChannel;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abbscoban.social.jwt.AuthResponse;
import com.abbscoban.social.jwt.JwtService;
import com.abbscoban.social.jwt.RefleshTokenRequest;
import com.abbscoban.social.model.RefleshToken;
import com.abbscoban.social.model.User;
import com.abbscoban.social.Repository.RefleshTokenRepository;

@Service
public class RefleshTokenService {

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

    public boolean isRefleshTokenExpired(Date expireDate) {
        return new Date().before(expireDate);
    }


    public AuthResponse refleshToken(RefleshTokenRequest request) {

        Optional<RefleshToken> optional = refleshTokenRepository.findByRefleshToken(request.getRefleshToken());

        if(optional.isEmpty()) {
            System.out.println("Reflesh token geçersiz"+ request.getRefleshToken());
        }

        RefleshToken refleshToken = optional.get();

        if(!isRefleshTokenExpired(refleshToken.getExpireDate())) {

            System.out.println("Reflesh Token Süresi Expired Olmuştur."+refleshToken.getExpireDate());
        }

        String accessToken = jwtService.generateToken(refleshToken.getUser());

        RefleshToken savedRefleshToken = refleshTokenRepository.save(createRefleshToken(refleshToken.getUser()));

        return new AuthResponse(accessToken, savedRefleshToken.getRefleshToken(),optional.get().getUser().getUsername(),optional.get().getUser().getCreateTime(),optional.get().getUser().getId());




    }

}

