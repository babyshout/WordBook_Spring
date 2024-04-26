package kopo.data.wordbook.app.controller.rest;

import jakarta.validation.Valid;
import kopo.data.wordbook.app.controller.request.GetStudentIdRequestBody;
import kopo.data.wordbook.app.controller.response.CommonApiResponse;
import kopo.data.wordbook.app.dto.MsgDTO;
import kopo.data.wordbook.app.service.IStudentService;
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
public class ForgotLogin {
    final private IStudentService studentService;

    @PostMapping("/get-id-list")
    public ResponseEntity getIdList(@Valid @RequestBody GetStudentIdRequestBody body, BindingResult bindingResult) {
        log.trace("body : " + body);
        if (bindingResult.hasErrors()) {
            log.warn("get Ids Error!!");
            return CommonApiResponse.getError(bindingResult);
        }

        if (!validBody(body.name(), body.email())) {
            HashMap<String, String> returningBodyForNotValidBody = new HashMap<String, String>();
            returningBodyForNotValidBody.put("message", "유효하지 않은 요청 본문 입니다!!");
            return ResponseEntity.badRequest().body(
                    returningBodyForNotValidBody
            );
        }


        List<String> rList = studentService.getStudentId(body.name(), body.email());

        if(rList == null) {
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

    private boolean validBody(String... args) {

        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
