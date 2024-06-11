package kopo.data.wordbook.app.student.social.naver.rest.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverLoginTokenRestClientTest {

    @Autowired
    NaverLoginTokenRestClient naverLoginTokenRestClient;

    @Test
    void authorization_code() {
        naverLoginTokenRestClient.get_authorization_code_uri("code1122", "state1122");
    }
}