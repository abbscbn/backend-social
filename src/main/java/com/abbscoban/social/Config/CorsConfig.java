package com.abbscoban.social.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm yollar için CORS ayarını yapıyoruz
                .allowedOrigins("https://social-frontend-production.up.railway.app") // React uygulamanızın origin adresi
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // İzin verilen HTTP metodları
                .allowedHeaders("*") // İzin verilen başlıklar
                .allowCredentials(true); // Kimlik doğrulama bilgilerini (cookie, header) almak için
    }

}

