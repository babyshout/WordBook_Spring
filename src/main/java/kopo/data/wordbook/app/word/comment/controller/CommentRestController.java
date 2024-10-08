package kopo.data.wordbook.app.word.comment.controller;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.famoussaying.service.IFamoussayingService;
import kopo.data.wordbook.app.notepad.controller.NotepadRestController;
import kopo.data.wordbook.app.notepad.controller.reponse.CreateNotepadResponse;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.comment.controller.request.CommentPostRequest;
import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntity;
import kopo.data.wordbook.app.word.comment.service.IWordCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public ResponseEntity<List<CommentEntity>> getCommentList(
            HttpSession session,
            @RequestParam String wordName
    ) {


        LogInController.LoginSessionInformation sessionInfo =
                LogInController.getLoginInformationFromSession(session);

        List<CommentEntity> commentList = wordCommentService.getCommentList(wordName, sessionInfo, session);

        return ResponseEntity.ok(commentList);

    }

    /**
     * TODO 테스트 필요함
     * TODO 작성필요
     * 댓글 생성 요청
     *
     * @param session
     * @param body
     * @return
     */
    @PostMapping()
    public ResponseEntity<CommentEntity> postCreateComment(
            HttpSession session,
            @RequestParam("wordName") String wordName,
            @RequestBody CommentPostRequest body
    ) {
        // session 정보를 가져옴
        LogInController.LoginSessionInformation sessionInfo =
                getSessionInfo(session);
        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
        if (!validSession(session, sessionInfo)) {
            return ResponseEntity.badRequest().build();
        }

        // 유효한 유저인지 검사가 끝났으면, notepad 를 생성함!
        CommentEntity responseBody = wordCommentService.createComment(
                body.content(), wordName, sessionInfo.studentId());

        return ResponseEntity.ok(responseBody);
    }

//    /**
//     * TODO 테스트 필요함
//     * TODO 작성필요
//     * 댓글 업데이트 요청
//     *
//     * @param session
//     * @param body
//     * @return
//     */
//    @PatchMapping()
//    public ResponseEntity<CommentEntity> patchComment(
//            HttpSession session,
//            @RequestParam("wordName") String wordName,
//            @RequestParam("wordCommentSeq") String wordCommentSeq,
//            @RequestBody CommentPostRequest body
//    ) {
//        // session 정보를 가져옴
//        LogInController.LoginSessionInformation sessionInfo =
//                getSessionInfo(session);
//        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
//        if (!validSession(session, sessionInfo)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        // 유효한 유저인지 검사가 끝났으면, notepad 를 생성함!
//        CommentEntity responseBody = wordCommentService.updateComment(
//                body.content(), wordName, sessionInfo.studentId());
//
//        return ResponseEntity.ok(responseBody);
//    }
//
//    /**
//     * TODO 테스트 필요함
//     * TODO 작성필요
//     * 댓글 삭제 요청
//     *
//     * @param session
//     * @return
//     */
//    @DeleteMapping
//    public ResponseEntity<CommentEntity> deleteComment(
//            HttpSession session,
//            @RequestParam("wordName") String wordName,
//            @RequestParam("wordCommentSeq") String wordCommentSeq
//    ) {
//        // session 정보를 가져옴
//        LogInController.LoginSessionInformation sessionInfo =
//                getSessionInfo(session);
//        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
//        if (!validSession(session, sessionInfo)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        // 유효한 유저인지 검사가 끝났으면, notepad 를 생성함!
//        CommentEntity responseBody = wordCommentService.deleteComment(
//                wordName, wordCommentSeq, sessionInfo.studentId());
//
//        return ResponseEntity.ok(responseBody);
//    }

    /**
     * {@link kopo.data.wordbook.app.student.controller.rest.LogInController.LoginSessionInformation} 를 가져옴
     * @param session {@link kopo.data.wordbook.app.student.controller.rest.LogInController.LoginSessionInformation} 가 들어있는 {@link HttpSession}
     * @return {@link HttpSession} 에 들어있는 {@link kopo.data.wordbook.app.student.controller.rest.LogInController.LoginSessionInformation}
     */
    private LogInController.LoginSessionInformation getSessionInfo(
            HttpSession session
    ) {
        return (LogInController.LoginSessionInformation)
                session.getAttribute(
                        LogInController.LoginSessionInformation.getName()
                );
    }

    private Boolean validSession(
            HttpSession session,
            LogInController.LoginSessionInformation sessionInfo
    ) {
        if (session.isNew() || sessionInfo == null) {
            log.warn("validSession 아님!!!");
        }
        return !session.isNew() || !(sessionInfo == null);
    }
}
