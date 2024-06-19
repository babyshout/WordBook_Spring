package kopo.data.wordbook.app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
public class CorsConfigurer {

    @Bean
    public CorsConfiguration myCorsConfigurer() {
//        CorsConfigurationSource

        CorsConfiguration corsConfig= new CorsConfiguration().applyPermitDefaultValues();
        log.error("cors getAllow Method : " + corsConfig.getAllowedMethods().toString());
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.PATCH);
        corsConfig.addAllowedHeader("*");

        log.error("cors getAllow Method : " + corsConfig.getAllowedMethods().toString());
//        corsConfig.addAllowedOrigin("http://localhost:5173/");
        /*
        When allowCredentials is true,
        allowedOrigins cannot contain the special value "*"
        since that cannot be set on the "Access-Control-Allow-Origin" response header.
        To allow credentials to a set of origins, list them explicitly
        or consider using "allowedOriginPatterns" instead.

        때문에 addAllowedHeader 가 아닌
        setAllowedOrigins 사용 해야함..
         */
//        corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:5173/"));
        corsConfig.setAllowedOrigins(
                List.of(
                        "http://localhost:5173/",
                        "http://27.96.134.201:4173",
                        "http://www.sumin.site:4173"
                )
        );
        return corsConfig;
    }
}
