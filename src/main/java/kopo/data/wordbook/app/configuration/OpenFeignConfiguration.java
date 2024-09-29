package kopo.data.wordbook.app.configuration;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("kopo.data.wordbook.app.famoussaying.openfeign")
public class OpenFeignConfiguration {
}
