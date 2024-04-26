package kopo.data.wordbook.app.controller.rest;

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

    @PostMapping("/getLogin")
    public ResponseEntity getLogin(@Valid @RequestBody LoginRequestBody loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonApiResponse.getError(bindingResult);
        }
        log.trace("loginRequest : " + loginRequest);


        LoginResponseData rData = studentService.getLogin(loginRequest.studentId(), loginRequest.password());
        log.trace("rData : " + rData);

        return ResponseEntity.ok(
                CommonApiResponse.of(
                        HttpStatus.OK,
                        HttpStatus.OK.series().name(),
                        rData
                )
        );
    }
}
