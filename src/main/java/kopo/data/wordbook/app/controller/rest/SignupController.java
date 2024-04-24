package kopo.data.wordbook.app.controller.rest;

import kopo.data.wordbook.app.controller.request.CreateStudentRequest;
import kopo.data.wordbook.app.controller.response.CommonApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/v1/signup")
@Slf4j
@CrossOrigin(
        originPatterns = {"http://localhost:5173"}
//        origins = "http:/localhost:5173"
)
public class SignupController {
    @PostMapping("/createStudent")
    public ResponseEntity createStudent(@ModelAttribute CreateStudentRequest requestBody){
        log.info("log.atDebug() + " + log.atDebug());
        log.info("log.getName() + " + log.getName());

        log.debug("requestBody : " + requestBody.toString());

        return ResponseEntity.ok(
                CommonApiResponse.of(
                        HttpStatus.OK,
                        HttpStatus.OK.series().name(),
                        "requestBody"
                )
        );
    }
}
