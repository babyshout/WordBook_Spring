package kopo.data.wordbook.app.word.search.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.search.controller.request.SearchWordRequst;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.service.ISearchWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/words/v1/search")
public class SearchWordRestController {

    private final ISearchWordService searchWordService;

    public final static class HandleUrl
    {
        public final static String postGetSearchWord = "/getSearchWord";
        public final static String getWordErrataCheck = "/wordErrataCheck";
    }

    @PostMapping(HandleUrl.postGetSearchWord)
    public ResponseEntity<SearchWordResponse> getSearchWord(
            HttpSession session,
            @RequestBody @Valid SearchWordRequst body
//            @PathVariable String word
    ) {
        log.trace("body -> {}", body);
        LogInController.LoginSessionInformation info =
                LogInController.getLoginInformationFromSession(session);
        log.trace("loginSessionInfo -> {}", info);

        SearchWordResponse response =
                searchWordService.searchWord(body.wordName(), info.studentId());


        return ResponseEntity.ok(response);
    }

    @GetMapping(HandleUrl.getWordErrataCheck)
    public ResponseEntity<String> getWordErrataCheck(
            HttpSession session,
            @RequestParam(value = "wordName", required = false) String wordName
    ) {
        log.trace("wordName by @RequestParam -> {}", wordName);

        return ResponseEntity.ok(
                searchWordService.wordErrataCheck(wordName)
        );

    }
}
