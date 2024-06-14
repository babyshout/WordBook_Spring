package kopo.data.wordbook.app.word.myword.controller;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.myword.controller.response.SimpleMywordResponse;
import kopo.data.wordbook.app.word.myword.service.MywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        public static final String getSimpleMywordList = "/myword/list/simple";
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
}
