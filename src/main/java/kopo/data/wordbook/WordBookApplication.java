package kopo.data.wordbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @CreatedDate, @LastModifiedDate 작동을 위해 추가
// @link https://wildeveloperetrain.tistory.com/76
@EnableJpaAuditing
@SpringBootApplication
public class WordBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordBookApplication.class, args);
    }

}
