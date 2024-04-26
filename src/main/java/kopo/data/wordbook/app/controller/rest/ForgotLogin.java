package kopo.data.wordbook.app.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/student/v1/login")
@RequiredArgsConstructor
@RestController
@CrossOrigin(originPatterns = {"http://localhost:5173"})
public class ForgotLogin {
}
