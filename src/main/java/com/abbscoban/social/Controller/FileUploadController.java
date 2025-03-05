package com.abbscoban.social.Controller;

import com.abbscoban.social.Repository.UserRepository;
import com.abbscoban.social.ResDto.ResponseUserDto;
import com.abbscoban.social.Service.UserService;
import com.abbscoban.social.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final String uploadDir = "uploads/";

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Dosya boş.");
            }

            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDir, uniqueFileName);
            Files.copy(file.getInputStream(), path);

            // Kullanıcının profil fotoğrafının yolunu veritabanında saklayabilirsiniz
            Optional<User> optUser = userRepository.findById(userId);
            if(optUser.isPresent()){

                User user =optUser.get();

                user.setProfilePicture(uniqueFileName);

                userRepository.save(user);

            }



            return ResponseEntity.ok("Dosya başarıyla yüklendi.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Dosya yüklenirken hata oluştu.");
        }
    }
}
