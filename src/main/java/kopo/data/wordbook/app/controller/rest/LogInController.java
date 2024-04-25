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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/student/v1/login")
@RequiredArgsConstructor
@RestController
public class LogInController {

    private final IStudentService studentService;

    @PostMapping("/getLogin")
    public ResponseEntity getLogin(@Valid @RequestBody LoginRequestBody loginRequest) {

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
