package kopo.data.wordbook.app.student.social.naver.login.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.student.social.naver.login.controller.request.NaverGetSocialLoginRequest;
import kopo.data.wordbook.app.student.social.naver.login.controller.response.NaverSocialLoginResponse;
import kopo.data.wordbook.app.student.social.naver.login.service.INaverSocialLoginService;
import kopo.data.wordbook.app.student.social.naver.rest.client.NaverLoginTokenRestClient;
import kopo.data.wordbook.app.student.social.naver.rest.client.response.AuthorizationCodeResponse;
import kopo.data.wordbook.app.student.social.naver.rest.client.response.NidMeResponse;
import kopo.data.wordbook.app.student.social.repository.SocialLoginRepository;
import kopo.data.wordbook.app.student.social.repository.entity.SocialLoginEntity;
import kopo.data.wordbook.app.student.social.repository.entity.SocialLoginEntityId;
import kopo.data.wordbook.app.student.social.repository.entity.constant.SocialLoginProvider;
import kopo.data.wordbook.common.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverSocialLoginService implements INaverSocialLoginService {

    private final NaverLoginTokenRestClient naverLoginTokenRestClient;

    private final StudentRepository studentRepository;

    private final SocialLoginRepository socialLoginRepository;


    @Override
    public NaverSocialLoginResponse getNaverSocialLogin(NaverGetSocialLoginRequest body, HttpSession session) {

        AuthorizationCodeResponse authorizationCode =
                naverLoginTokenRestClient.get_authorization_code(body.code(), body.state());

        log.trace("authorizationCode -> {}", authorizationCode);

        String accessToken = authorizationCode.access_token();

        NidMeResponse nidmeResponse = naverLoginTokenRestClient.getNidmeResponse(accessToken);

        // TODO nidmeResponse 로 회원가입 및 로그인..

        LogInController.LoginSessionInformation sessionInfo = this.registerOrLoginByNaverInfo(nidmeResponse);

        LogInController.setLoginSessionInfoToSession(sessionInfo, session);

        return null;
    }

    @Override
    public LogInController.LoginSessionInformation registerOrLoginByNaverInfo(
            NidMeResponse nidmeResponse
    ) {
        String idOfNidme = nidmeResponse.response().id();
        String nameOfNidme = nidmeResponse.response().name();
        String emailOfNidme = nidmeResponse.response().email();

        SocialLoginEntityId socialLoginId =
                SocialLoginEntityId.builder()
                        .id_bySocialLoginProvider(idOfNidme)
                        .provider(SocialLoginProvider.NAVER)
                        .build();

        Optional<SocialLoginEntity> socialLoginEntityById =
                socialLoginRepository.findById(socialLoginId);

        if (socialLoginEntityById.isPresent()) {
            return this.loginByExistsSocialLoginEntity(socialLoginEntityById.get());
        }

        LogInController.LoginSessionInformation sessionInfo =
                this.registerByNidMeResponse(socialLoginId, idOfNidme, emailOfNidme, nameOfNidme);

        return sessionInfo;
    }


    /**
     * 받아온 session Info 로 회원가입 및 회원가입한 entity 로 LoginSessionInformation 받아옴
     *
     * @param socialLoginEntityId
     * @param idOfNidme
     * @param emailOfNidme
     * @param nameOfNidme
     * @return
     */
    private LogInController.LoginSessionInformation registerByNidMeResponse(
            SocialLoginEntityId socialLoginEntityId,
            String idOfNidme,
            String emailOfNidme,
            String nameOfNidme
    ) {
        StudentEntity student = StudentEntity.builder()
                .studentId(idOfNidme)
                .password("1234")
                .email(EncryptUtil.encAES128CBC(emailOfNidme))
                .regId(idOfNidme)
                .changerId(idOfNidme)
                .name(nameOfNidme)
                .build();

        StudentEntity savedStudent = studentRepository.save(student);

        log.trace("savedStudent -> {}", savedStudent);


//        SocialLoginEntity savingSocialEntity = SocialLoginEntity.builder()
//                .id_bySocialLoginProvider(idOfNidme)
//                .email(EncryptUtil.encAES128CBC(emailOfNidme))
//                .provider(SocialLoginProvider.NAVER)
//                .name(nameOfNidme)
//                .student(savedStudent).build();
        log.trace("socialLoginEntityId -> {}", socialLoginEntityId);
        SocialLoginEntity savingSocialEntity =
                SocialLoginEntity.builder()
                        .primaryKey(socialLoginEntityId)

                        .student(savedStudent)
                        .email(EncryptUtil.encAES128CBC(emailOfNidme))
                        .name(nameOfNidme)
                        .build();
        log.trace("savingSocialEntity -> {}", savingSocialEntity);

        SocialLoginEntity savedSocialLogin = socialLoginRepository.save(savingSocialEntity);

        log.trace("savedSocialLogin -> {}", savedSocialLogin);

        LogInController.LoginSessionInformation sessionInfo =
                LogInController.LoginSessionInformation.of(savedStudent);
        log.trace("sessionInfo -> {}", sessionInfo);

        return sessionInfo;
    }

    /**
     * 이미 있는 회원정보로 LoginSessionInformation 받아옴..
     *
     * @param socialLoginEntity
     * @return
     */
    private LogInController.LoginSessionInformation loginByExistsSocialLoginEntity(SocialLoginEntity socialLoginEntity) {

        StudentEntity entity = socialLoginEntity.getStudent();

        LogInController.LoginSessionInformation sessionInfo = LogInController.LoginSessionInformation.of(entity);
        log.trace("sessionInfo -> {}", sessionInfo);

        return sessionInfo;
    }
}
