package kopo.data.wordbook.app.word.search.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
import kopo.data.wordbook.app.word.search.controller.request.SearchWordRequst;
import kopo.data.wordbook.app.word.search.controller.response.RecentlySearchWord;
import kopo.data.wordbook.app.word.search.controller.response.SearchWordResponse;
import kopo.data.wordbook.app.word.search.controller.response.SimpleWordResponse;
import kopo.data.wordbook.app.word.search.service.ISearchWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/words/v1/search")
public class SearchWordRestController {

    private final ISearchWordService searchWordService;

    public final static class HandleUrl {
        public final static String postGetSearchSimpleWordList = "/getSearchSimpleWordList";
        public final static String getWordErrataCheck = "/wordErrataCheck";
        public final static String getSearchRecentlySearchWord = "/searchRecentlySearchWord";
        public final static String getTodaySearchWord = "/todaySearchWord";
    }

    @PostMapping(HandleUrl.postGetSearchSimpleWordList)
    public ResponseEntity<List<SimpleWordResponse>> getSearchWord(
            HttpSession session,
            @RequestBody @Valid SearchWordRequst body
//            @PathVariable String word
    ) {
        log.trace("body -> {}", body);
        LogInController.LoginSessionInformation info =
                LogInController.getLoginInformationFromSession(session);
        log.trace("loginSessionInfo -> {}", info);

//        SearchWordResponse response =
//                searchWordService.searchWordDetail(body.wordName(), info.studentId());

        List<SimpleWordResponse> response =
                searchWordService.searchSimpleWordList(body.wordName());


        return ResponseEntity.ok(response);
    }

    @GetMapping(HandleUrl.getSearchRecentlySearchWord)
    public ResponseEntity<List<RecentlySearchWord>> getSearchRecentlySearchWord(
            HttpSession session
    ) {
        LogInController.LoginSessionInformation info =
                LogInController.getLoginInformationFromSession(session);
        log.trace("loginSessionInfo -> {}", info);

        List<RecentlySearchWord> response =
                searchWordService.getSearchRecentlySearchWord(info.studentId());

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

    @GetMapping(HandleUrl.getTodaySearchWord)
    public ResponseEntity<WordDocument> getTodaySearchWord() {
        WordDocument wordDocument = searchWordService.getTodaySearchWord();

        return ResponseEntity.ok(wordDocument);
    }
}
