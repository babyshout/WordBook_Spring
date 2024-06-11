package kopo.data.wordbook.app.student.social.naver.login.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.social.naver.login.controller.request.NaverGetSocialLoginRequest;
import kopo.data.wordbook.app.student.social.naver.login.controller.response.NaverSocialLoginResponse;
import kopo.data.wordbook.app.student.social.naver.login.service.INaverSocialLoginService;
import kopo.data.wordbook.app.student.social.naver.rest.client.NaverLoginTokenRestClient;
import kopo.data.wordbook.app.student.social.naver.rest.client.response.AuthorizationCodeResponse;
import kopo.data.wordbook.app.student.social.naver.rest.client.response.NidMeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverSocialLoginService implements INaverSocialLoginService {

    private final NaverLoginTokenRestClient naverLoginTokenRestClient;

    @Override
    public NaverSocialLoginResponse getNaverSocialLogin(NaverGetSocialLoginRequest body, HttpSession session) {

        AuthorizationCodeResponse authorizationCode =
                naverLoginTokenRestClient.get_authorization_code(body.code(), body.state());

        log.trace("authorizationCode -> {}", authorizationCode);

        String accessToken = authorizationCode.access_token();

        NidMeResponse nidmeResponse = naverLoginTokenRestClient.getNidmeResponse(accessToken);

        // TODO nidmeResponse 로 회원가입 및 로그인..

        return null;
    }
}
