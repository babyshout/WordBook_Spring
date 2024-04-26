package kopo.data.wordbook.app.controller.rest;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.controller.request.LoginRequestBody;
import kopo.data.wordbook.app.controller.response.CommonApiResponse;
import kopo.data.wordbook.app.controller.response.LoginResponseData;
import kopo.data.wordbook.app.service.IStudentService;
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
@CrossOrigin(originPatterns = {"http://localhost:5173"})
public class LogInController {

    private final IStudentService studentService;

    /**
     * TODO 회원가입할때 암호화하기 쿠키슬대 정보 노출되면 안됨, JWT 토큰 -> 암호화되어잇어서.. 왠만하면 쿠키로 저장하는건 권장 안함
     * msa 아니면 세션 -> 가장 안전함 msa 는 jwt 서야되가지고..
     * session -> 해킹될 위험이 거의 없음..
     * 토큰 -> 토큰 유효한지..
     * validation 
     * 
     * msa -> 이게 맞는데 아니면 세션에 저장해야
     * 아니면 매번 세션에 접속
     * 
     * 스프링시큐리티 스면 좋고, 안되면 매번 로딩할대마다 체크
     * @param loginRequest
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("/getLogin")
    public ResponseEntity getLogin(
            @Valid @RequestBody LoginRequestBody loginRequest,
            BindingResult bindingResult,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return CommonApiResponse.getError(bindingResult);
        }
        log.trace("loginRequest : " + loginRequest);


        LoginResponseData rData = studentService.getLogin(
                loginRequest.studentId(), loginRequest.password()
        );
        log.trace("rData : " + rData);
        session.setAttribute("loginInfo", rData);
        log.trace("loginInfo in session : " + rData);

        return ResponseEntity.ok(
                CommonApiResponse.of(
                        HttpStatus.OK,
                        HttpStatus.OK.series().name(),
                        rData
                )
        );
    }
//    enum SESSION_ENUM
}
