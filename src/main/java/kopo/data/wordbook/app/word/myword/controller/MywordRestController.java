package kopo.data.wordbook.app.word.myword.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.myword.controller.request.PostAddNewMywordRequest;
import kopo.data.wordbook.app.word.myword.controller.request.PostWordNameToMywordRequest;
import kopo.data.wordbook.app.word.myword.controller.response.MywordDetailResponse;
import kopo.data.wordbook.app.word.myword.controller.response.MywordResponse;
import kopo.data.wordbook.app.word.myword.controller.response.SimpleMywordResponse;
import kopo.data.wordbook.app.word.myword.service.MywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/words/v1/myword")
public class MywordRestController {

    private final MywordService mywordService;

    /**
     * mapping 되는 URL 관리..
     */
    public static final class MappedUrl {
        public static final String getSimpleMywordList = "/list/simple";
        public static final String postAddNewMyword = "/addNewMyword";
        public static final String postWordNameToMyword = "/wordNameToMyword";
        public static final String getMywordList = "/list";
        public static final String getMywordDetail = "/detail";
    }

    /**
     * {@link kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity}
     * session 에 들어있는 student ID 로 myword 를 조회해서 가져옴
     *
     * @param session student's ID 가 반드시 들어있어야함
     * @return 조회결과
     */
    @GetMapping(MappedUrl.getSimpleMywordList)
    public ResponseEntity<List<SimpleMywordResponse>> getSimpleMywordList(
            HttpSession session
    ) {
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        List<SimpleMywordResponse> responseList =
                mywordService.getSimpleMywordList(sessionInfo.studentId());

        return ResponseEntity.ok(responseList);
    }

    @PostMapping(MappedUrl.postAddNewMyword)
    public ResponseEntity<List<SimpleMywordResponse>> postAddNewMyword(
            HttpSession session,
            @RequestBody @Valid PostAddNewMywordRequest body
    ) {
        log.trace("body -> {}", body);
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        List<SimpleMywordResponse> responseList =
                mywordService.addNewMyword(body.newMywordName(), sessionInfo.studentId());

        return ResponseEntity.ok(responseList);
    }

    @PostMapping(MappedUrl.postWordNameToMyword)
    public ResponseEntity<MywordResponse> postWordNameToMyword(
            HttpSession session,
            @RequestBody @Valid PostWordNameToMywordRequest body
    ) {
        log.trace("body -> {}", body);

        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        MywordResponse mywordResponse =
                mywordService.postWordNameToMyword(body, sessionInfo.studentId());

        return ResponseEntity.ok(mywordResponse);
    }

    /**
     *
     * @param session
     * @return
     */
    @GetMapping(MappedUrl.getMywordList)
    public ResponseEntity<List<MywordResponse>> getMywordList(
            HttpSession session
    ) {
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        List<MywordResponse> responseList =
                mywordService.getMywordResponseList(sessionInfo.studentId());

        return ResponseEntity.ok(responseList);
    }

    /**
     *
     * @param session
     * @param mywordName
     * @return
     */
    @GetMapping(MappedUrl.getMywordDetail)
    public ResponseEntity<MywordDetailResponse> getMywordDetail(
            HttpSession session,
            @RequestParam String mywordName
    ) {
        log.trace("requestParma mywordName -> {}", mywordName);
        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        MywordDetailResponse response =
                mywordService.getMywordDetailResponseList(mywordName, sessionInfo.studentId());

        return ResponseEntity.ok(response);
    }
}
