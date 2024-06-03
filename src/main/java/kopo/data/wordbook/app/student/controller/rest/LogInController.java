package kopo.data.wordbook.app.student.controller.rest;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.request.LoginRequestBody;
import kopo.data.wordbook.app.student.controller.response.CommonApiResponse;
import kopo.data.wordbook.app.student.controller.response.LoginResponseData;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.student.service.IStudentService;
import kopo.data.wordbook.common.util.EncryptUtil;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/student/v1/login")
@RequiredArgsConstructor
@RestController
//@CrossOrigin(
//        originPatterns = {"http://localhost:5173"}
////,allowCredentials = "true"
//)
public class LogInController {

    @RequiredArgsConstructor
    @Getter
    public enum HandleURL {
        BASE("/api/student/v1/login"),
        GET_LOGIN(Paths.GET_LOGIN),
        ;

        //        @JsonSubTypes()
        public class Paths {
            public static final String BASE_PATH = "/api/student/v1/login";
            public static final String GET_LOGIN = "/getLogin";
            public static final String DELETE_LOGIN_SESSION_INFORMATION =
                    "/deleteLoginSessionInformation";
            public static final String GET_LOGIN_SESSION_INFORMATION =
                    "/getLoginSessionInformation";
            public static final String LOGIN_SESSION_INFORMATION =
                    "/loginSessionInformation";

            public static String getFullLoginSessionInformationPath() {
                return BASE_PATH + LOGIN_SESSION_INFORMATION;
            }
        }

        public final String path;


    }

    private final HttpSession session;

    private final IStudentService studentService;

    /**
     * TODO 회원가입할때 암호화하기 쿠키슬대 정보 노출되면 안됨, JWT 토큰 -> 암호화되어잇어서.. 왠만하면 쿠키로 저장하는건 권장 안함
     * msa 아니면 세션 -> 가장 안전함 msa 는 jwt 서야되가지고..
     * session -> 해킹될 위험이 거의 없음.. (서버에서만 사용해서)
     * 토큰 -> 토큰 유효한지..
     * 토큰쓰면 validation 해줘야 되는거고..
     * <p>
     * msa 쓰면 이게 맞는데 아니면 세션에 저장해야
     * <p>
     * 아니면 매번 세션에 접속해서(서버), 세션에 들어있는 정보 가져오도록..
     * <p>
     * 스프링시큐리티 쓰면 좋고, 안되면 매번 로딩할대마다 체크
     * <p>
     * 쿠키에 들어있는거 민감한 정보는 빼버리고,, 정 쓰고싶으면 암호화해서 사용
     *
     * @param loginRequest
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping(value = HandleURL.Paths.GET_LOGIN)
    public ResponseEntity getLogin(
            @Valid @RequestBody LoginRequestBody loginRequest,
            BindingResult bindingResult,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return CommonApiResponse.getError(bindingResult);
        }
        log.trace("loginRequest : " + loginRequest);

        // NOTE password 암호화 위해 처리..
        loginRequest = LoginRequestBody.builder()
                .studentId(loginRequest.studentId())
                .password(EncryptUtil.encHashSHA256(
                        loginRequest.password()
                ))
                .build();


        LoginResponseData rData = studentService.getLogin(
                loginRequest.studentId(), loginRequest.password()
        );
        log.trace("rData : " + rData);
//        session.setAttribute("loginInfo", rData);
//        log.trace("loginInfo in session : " + rData);

        if (rData.isLogin()) {
            StudentEntity studentEntity = studentService.getStudentById(loginRequest.studentId());
            LoginSessionInformation info = LoginSessionInformation.of(studentEntity);
            setLoginSessionInfoToSession(info, session);

            log.trace("set login info to session {}",
                    this.getLoginInformationFromSession(session).toString());
        }

        return ResponseEntity.ok(
                CommonApiResponse.of(
                        HttpStatus.OK,
                        HttpStatus.OK.series().name(),
                        rData
                )
        );
    }

    private void setLoginSessionInfoToSession(LoginSessionInformation info, HttpSession session) {
        log.trace("loginInfoName : {}", LoginSessionInformation.class.getName());

        session.setAttribute(LoginSessionInformation.class.getName(),
                info);

    }


    @Builder
    public record LoginSessionInformation(
            String studentId,
            String name,
            String email
    ) {
        public static LoginSessionInformation of(StudentEntity entity) {
            return LoginSessionInformation.builder()
                    .studentId(entity.getStudentId())
                    .email(entity.getEmail())
                    .name(entity.getName()).build();
        }

        public static String getName() {
            return LoginSessionInformation.class.getName();
        }

    }


    @GetMapping(HandleURL.Paths.LOGIN_SESSION_INFORMATION)
    public ResponseEntity<LoginSessionInformation> getLoginSessionInformation(HttpSession session) {
        log.trace(this.getLoginInformationFromSession(session).toString());

        return ResponseEntity.ok(getLoginInformationFromSession(session));
//        return null;
    }

    @DeleteMapping(HandleURL.Paths.LOGIN_SESSION_INFORMATION)
    public ResponseEntity<String> deleteLoginSessionInformation(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok("login session info deleted!");
    }

    private LoginSessionInformation getLoginInformationFromSession(HttpSession session) {
        log.trace("loginInfoName : {}", LoginSessionInformation.class.getName());
        return (LoginSessionInformation)
                session.getAttribute(LoginSessionInformation.class.getName());
    }
}
