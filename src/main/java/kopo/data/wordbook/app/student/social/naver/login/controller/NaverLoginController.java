package kopo.data.wordbook.app.student.social.naver.login.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.social.naver.login.controller.request.NaverGetSocialLoginRequest;
import kopo.data.wordbook.app.student.social.naver.login.controller.response.NaverSocialLoginResponse;
import kopo.data.wordbook.app.student.social.naver.login.service.INaverSocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/student/v1")
public class NaverLoginController {

    private final INaverSocialLoginService naverSocialLoginService;

    public class HandleUrl{
        public static final String getSocialLogin = "/naver/getSocialLogin";
    }

    @PostMapping(HandleUrl.getSocialLogin)
    public ResponseEntity getSocialLogin(
            HttpSession session,
            @RequestBody @Valid NaverGetSocialLoginRequest body
    ) {
        log.trace("requestBody -> {}", body);

        NaverSocialLoginResponse response = naverSocialLoginService.getNaverSocialLogin(body, session);

        return null;
    }

    private void test() {
        UriComponentsBuilder.fromHttpUrl("1234")
                .build();
    }
}
