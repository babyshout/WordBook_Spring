package kopo.data.wordbook.app.student.social.naver.login.service;


import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.student.social.naver.login.controller.request.NaverGetSocialLoginRequest;
import kopo.data.wordbook.app.student.social.naver.login.controller.response.NaverSocialLoginResponse;
import kopo.data.wordbook.app.student.social.naver.rest.client.response.NidMeResponse;

public interface INaverSocialLoginService {
    NaverSocialLoginResponse getNaverSocialLogin(NaverGetSocialLoginRequest body, HttpSession session);

    LogInController.LoginSessionInformation registerOrLoginByNaverInfo(NidMeResponse nidmeResponse);
}

