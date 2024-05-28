package kopo.data.wordbook.app.student.controller.rest;

import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.request.GetStudentIdRequestBody;
import kopo.data.wordbook.app.student.controller.response.CommonApiResponse;
import kopo.data.wordbook.app.student.dto.CommonData;
import kopo.data.wordbook.app.student.dto.MsgDTO;
import kopo.data.wordbook.app.student.service.IStudentService;
import kopo.data.wordbook.common.util.EncryptUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/api/student/v1/forgot")
@RequiredArgsConstructor
@RestController
@CrossOrigin(originPatterns = {"http://localhost:5173"})
public class ForgotLoginController {
    final private IStudentService studentService;
    private final CommonData commonData = new CommonData();

    @PostMapping("/get-id-list")
    public ResponseEntity getIdList(@Valid @RequestBody GetStudentIdRequestBody body, BindingResult bindingResult) {
        log.trace("body : " + body);
        if (bindingResult.hasErrors()) {
            log.warn("get Ids Error!!");
            return CommonApiResponse.getError(bindingResult);
        }

        if (!commonData.validBody(body.name(), body.email())) {
            HashMap<String, String> returningBodyForNotValidBody = new HashMap<>();
            returningBodyForNotValidBody.put("message", "유효하지 않은 요청 본문 입니다!!");
            return ResponseEntity.badRequest().body(
                    returningBodyForNotValidBody
            );
        }


        // email 암호화 안하면 안넘어감..
        List<String> rList = studentService.getStudentIdList(
                body.name(),
                EncryptUtil.encAES128CBC(body.email())
        );

        if (rList == null) {
            log.error("rList is NULL!!!");
            return ResponseEntity.ok(
                    CommonApiResponse.of(
                            HttpStatus.OK,
                            HttpStatus.OK.series().name(),
                            MsgDTO.builder()
                                    .message("해당 내용이 없습니다!")
                                    .result(false).build()
                    )
            );
        }

        return ResponseEntity.ok(
                CommonApiResponse.of(
                        HttpStatus.OK,
                        HttpStatus.OK.series().name(),
                        rList
                )
        );
    }

    @Builder
    public record ResetPasswordForIdRequestBody(
            String studentId,
            String name,
            String email
    ) {
    }

    @PostMapping("reset-password-for-id")
    public ResponseEntity<String> resetPasswordForId(
            @Valid @RequestBody ForgotLoginController.ResetPasswordForIdRequestBody body
    ) {
        final String resultMessage = studentService.resetPasswordForId(
                body.studentId,
                body.name,
                EncryptUtil.encAES128CBC(body.email)
        );

//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        return ResponseEntity.ok(resultMessage);
    }

    @Builder
    public record ResetPasswordForIdResponseBody(
            String resultMessage
    ){

    }
//    @PostMapping("reset-password-for-id")
//    public ResponseEntity<ResetPasswordForIdResponseBody> resetPasswordForId(
//            @Valid @RequestBody ForgotLoginController.ResetPasswordForIdRequestBody body
//    ) {
//        final String resultMessage = studentService.resetPasswordForId(
//                body.studentId,
//                body.name,
//                EncryptUtil.encAES128CBC(body.email)
//        );
//
////        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
//        return ResponseEntity.ok(ResetPasswordForIdResponseBody.builder()
//                .resultMessage(resultMessage).build());
//    }

    private boolean validBody(String... args) {
        return commonData.validBody(args);
    }
}
