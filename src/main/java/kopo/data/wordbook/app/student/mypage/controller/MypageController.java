package kopo.data.wordbook.app.student.mypage.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.student.mypage.controller.request.PatchStudentInfoRequest;
import kopo.data.wordbook.app.student.mypage.controller.request.PatchStudentPasswordRequest;
import kopo.data.wordbook.app.student.mypage.controller.request.PostDeleteStudentAccountRequest;
import kopo.data.wordbook.app.student.mypage.response.EmailAuthCodeResponse;
import kopo.data.wordbook.app.student.mypage.response.StudentInfo;
import kopo.data.wordbook.app.student.mypage.service.IMypageService;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.student.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kopo.data.wordbook.app.student.controller.rest.LogInController.getLoginInformationFromSession;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/student/v1/mypage")
public class MypageController {

    private final IMypageService mypageService;
    private final IStudentService studentService;

    public static class HandleUrl {
        static public final String getStudentInfoBySession = "/getStudentInfoBySession";
        static public final String getEmailAuthCode = "/getEmailAuthCode";
        static public final String patchStudentInfo = "/studentInfo";
        static public final String patchStudentPassword = "/studentPassword";

        static public final String postDeleteStudentAccount = "/deleteStudentAccount";
    }

    @GetMapping(HandleUrl.getStudentInfoBySession)
    public ResponseEntity<StudentInfo> getStudentInfoBySession(
            HttpSession session
    ) {
//        StudentEntity.builder().
        LogInController.LoginSessionInformation sessionInfo = getLoginInformationFromSession(session);

        StudentInfo studentInfo = mypageService.getStudentInfoByRepository(sessionInfo.studentId());


        return ResponseEntity.ok(studentInfo);
    }

    @PostMapping(HandleUrl.getEmailAuthCode)
    public ResponseEntity getEmailAuthCode(
            HttpSession session,
            @RequestBody GetEmailAuthRequest request
    ) {
        LogInController.LoginSessionInformation sessionInfo = LogInController.getLoginInformationFromSession(session);
//        String email = sessionInfo.email();

        log.trace("request.email() -> {}", request.email());
        log.trace("sessionInfo.email() -> {}", sessionInfo.email());

        EmailAuthCodeResponse response = mypageService.getEmailAuthCode(request.email());

        session.setAttribute(EmailAuthCodeResponse.class.getName(), response.authCode());

        return ResponseEntity.ok(response);
    }

    public record GetEmailAuthRequest(
            String email
    ) {

    }

    @PatchMapping(HandleUrl.patchStudentInfo)
    public ResponseEntity patchStudentInfo(
            HttpSession session,
            @RequestBody PatchStudentInfoRequest request
    ) {
        log.trace("request -> {}", request);

        LogInController.LoginSessionInformation loginSessionInfo = getLoginInformationFromSession(session);


        String authCodeBySession = (String) session.getAttribute(EmailAuthCodeResponse.class.getName());
        session.removeAttribute(EmailAuthCodeResponse.class.getName());

        Boolean isSuccess = mypageService.patchStudentInfo(request, loginSessionInfo.studentId(), authCodeBySession);

        if (!isSuccess) {
            log.warn("isSuccess -> {}", isSuccess);
            return ResponseEntity.internalServerError().build();
        }
        StudentEntity student = studentService.getStudentById(loginSessionInfo.studentId());
        LogInController.LoginSessionInformation info = LogInController.LoginSessionInformation.of(student);
        LogInController.setLoginSessionInfoToSession(info, session);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(HandleUrl.patchStudentPassword)
    public ResponseEntity<String> patchStudentPassword(
            HttpSession session,
            @RequestBody @Valid PatchStudentPasswordRequest request
    ) {

        log.trace("request -> {}", request);
//        throw new RuntimeException("runTime Exception!!");
        LogInController.LoginSessionInformation loginSessionInfo = getLoginInformationFromSession(session);
        Boolean result = mypageService.patchStudentPassword(request, loginSessionInfo.studentId());
        log.trace("service calling result -> {}", result);

        return ResponseEntity.ok("password 변경 성공");
    }

    @PostMapping(HandleUrl.postDeleteStudentAccount)
    public ResponseEntity<String> postDeleteStudentAccount(
            HttpSession session,
            @RequestBody @Valid PostDeleteStudentAccountRequest request
    ) {
        log.trace("request -> {}", request);
        LogInController.LoginSessionInformation info = getLoginInformationFromSession(session);

        Boolean result = mypageService.deleteStudentAccount(request, info.studentId());
        log.trace("service calling result -> {}", result);

        session.invalidate();

        return ResponseEntity.ok("student 삭제 성공");
    }
}
