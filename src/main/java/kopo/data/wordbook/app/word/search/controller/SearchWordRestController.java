package kopo.data.wordbook.app.word.search.controller;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.service.ISearchWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/words/v1/search")
public class SearchWordRestController {

    private final ISearchWordService searchWordService;

    public final static class HandleUrl
    {
        public final static String getSearchWordName = "/{word}";
    }

    @GetMapping(HandleUrl.getSearchWordName)
    public ResponseEntity<SearchWordResponse> getSearchWord(
            HttpSession session,
            @PathVariable String word
    ) {
        log.trace("word by path variable -> {}", word);
        LogInController.LoginSessionInformation info =
                LogInController.getLoginInformationFromSession(session);
        log.trace("loginSessionInfo -> {}", info);

        SearchWordResponse response =
                searchWordService.searchWord(word, info.studentId());


        return ResponseEntity.ok(response);
    }
}
