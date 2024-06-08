package kopo.data.wordbook.app.student.controller.rest;

import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.request.CreateStudentRequest;
import kopo.data.wordbook.app.student.controller.response.CommonApiResponse;
import kopo.data.wordbook.app.student.dto.MsgDTO;
import kopo.data.wordbook.app.student.dto.StudentDTO;
import kopo.data.wordbook.app.student.service.IStudentService;
import kopo.data.wordbook.common.util.EncryptUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/student/v1/signup")
@Slf4j
@CrossOrigin(
        originPatterns = {"http://localhost:5173"}
//        origins = "http:/localhost:5173"
)
public class SignupController {

    private final IStudentService studentService;


    public SignupController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/createStudent")
    public ResponseEntity createStudent(
            @Valid @RequestBody CreateStudentRequest requestBody,
            BindingResult bindingResult
    ) {
        log.info("log.atDebug() + " + log.atDebug());
        log.info("log.getName() + " + log.getName());
        if (bindingResult.hasErrors()) {
            return CommonApiResponse.getError(bindingResult);
        }
        log.debug("requestBody : " + requestBody.toString());

        requestBody = CreateStudentRequest.builder()
                .studentId(requestBody.studentId())
                .email(
                        EncryptUtil.encAES128CBC(
                                requestBody.email())
                )
                .emailVerificationCode(requestBody.emailVerificationCode())
                .name(requestBody.name())
                .password(
                        EncryptUtil.encHashSHA256(requestBody.password())
                )
                .passwordConfirm(
                        EncryptUtil.encHashSHA256(requestBody.passwordConfirm())
                )
                .build();


        StudentDTO pDTO = StudentDTO.of(requestBody);
        log.debug("pDTO to service : " + pDTO);


        MsgDTO rDTO = studentService.createStudent(pDTO);
        log.debug("rDTO : " + rDTO);

        // 회원가입이 실패한다면
        if (!rDTO.result()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rDTO);
        }


        return ResponseEntity.ok(
                CommonApiResponse.of(
                        HttpStatus.OK,
                        HttpStatus.OK.series().name(),
                        rDTO
                )
        );
    }

    @Builder
    public record EmailVerificationRequest(
//            @NotBlank(message = "email 입력해주세요")
            String email
    ) {
    }

    @PostMapping("/getVerificationCode")
    public ResponseEntity<EmailVerificationCodeResult> getEmailVerificationCode(
            @RequestBody EmailVerificationRequest body
    ) {
        log.trace("body.email() -> {}", body.email());
        EmailVerificationCodeResult result = studentService.getEmailVerificationCodeWhenSignup(body.email());

        return ResponseEntity.ok(result);
    }

    @Builder
    public record EmailVerificationCodeResult(
            String code,
            Boolean isEmailExists
    ) {

    }
}
