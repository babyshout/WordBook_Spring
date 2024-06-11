package kopo.data.wordbook.app.student.social.naver.login.service;


import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.social.naver.login.controller.request.NaverGetSocialLoginRequest;
import kopo.data.wordbook.app.student.social.naver.login.controller.response.NaverSocialLoginResponse;

public interface INaverSocialLoginService {
    NaverSocialLoginResponse getNaverSocialLogin(NaverGetSocialLoginRequest body, HttpSession session);
}

