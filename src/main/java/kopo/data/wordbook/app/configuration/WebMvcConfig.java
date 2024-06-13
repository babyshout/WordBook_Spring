package kopo.data.wordbook.app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private final CorsConfiguration myCorsConfigurer;

    public WebMvcConfig(CorsConfiguration myCorsConfigurer) {
        this.myCorsConfigurer = myCorsConfigurer;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.error("myCorsConfigurer {}", myCorsConfigurer.getAllowedMethods());

//        registry.addMapping("/**")
        // allowedOrigins 가 되는이유는.. registry 내부의 CorsRegistry 에서 allowedOrigins()
        // 호출시 setAllowedOrigins 를 호출하기 때문!!
//                .allowedOrigins("http://localhost:5173")
//                .allowedMethods("GET","POST","PUT","DELETE")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600);

        registry.addMapping("/**")
                .allowedOriginPatterns("**")
                .combine(myCorsConfigurer);
    }
}
