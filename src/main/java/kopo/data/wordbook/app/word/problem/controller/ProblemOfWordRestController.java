package kopo.data.wordbook.app.word.problem.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.problem.controller.request.RandomWordDocumentToSolveResultRequest;
import kopo.data.wordbook.app.word.problem.service.ProblemOfWordService;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/words/v1/problem-of-word")
public class ProblemOfWordRestController {

    private final ProblemOfWordService service;
    public static final class MappedUrl{
        public static final String getRandomWordDocumentToSolve = "/randomWordDocument/toSolve";
        public static final String postRandomWordDocumentToSolveResult = "/randomWordDocument/toSolveResult";
    }

    @GetMapping(MappedUrl.getRandomWordDocumentToSolve)
    public ResponseEntity<WordDocument> getRandomWordDocumentToSolve(
            HttpSession session,
            @RequestParam String mywordName
    ){
        log.trace("요청 파라미터의 mywordName -> {}", mywordName);

        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        WordDocument wordDocument = service.getRandomWordDocumentToSolve(mywordName, sessionInfo.studentId(), session);

        return ResponseEntity.ok(wordDocument);
    }

    @PostMapping(MappedUrl.postRandomWordDocumentToSolveResult)
    public ResponseEntity postRandomWordDocumentToSolveResult(
            HttpSession session,
            @RequestBody @Valid RandomWordDocumentToSolveResultRequest body
    ) {
        log.trace("body -> {}", body);

        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        service.postRandomWordDocumentToSolveResult(body, sessionInfo.studentId(), session);

        return ResponseEntity.ok().build();
    }
}
