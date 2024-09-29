package kopo.data.wordbook.app.word.comment.controller;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.famoussaying.service.IFamoussayingService;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntity;
import kopo.data.wordbook.app.word.comment.service.IWordCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/words/v1/comment")
public class CommentRestController {

    private final IWordCommentService wordCommentService;

    /**
     * 이 메서드에서 핸들링하는 url 을 관리
     */
    public static class HandleUrl {
        public static final String getKoreanAdviceOpenApi =
                "/korean-advice-open-api";
    }
    @GetMapping
    public ResponseEntity<List<CommentEntity>> get(
            HttpSession session,
            @RequestParam String wordName
    ) {


        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        List<CommentEntity> commentList = wordCommentService.getCommentList(wordName, sessionInfo, session);

        return ResponseEntity.ok(commentList);

    }
}
