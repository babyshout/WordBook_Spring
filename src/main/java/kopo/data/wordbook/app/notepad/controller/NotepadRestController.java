package kopo.data.wordbook.app.notepad.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kopo.data.wordbook.app.notepad.controller.reponse.CreateNotepadResponse;
import kopo.data.wordbook.app.notepad.controller.reponse.GetNotepadResponse;
import kopo.data.wordbook.app.notepad.service.INotepadService;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notepads/v1")
@Slf4j
@RequiredArgsConstructor
public class NotepadRestController {

    private final INotepadService notepadService;

    /**
     * 이 메서드에서 핸들링하는 url 을 관리
     */
    public static class HandleUrl {
        public static final String getNotepad =
                "/notepad/{notepadSeq}";
        public static final String getNotepadList =
                "/notepad/getList";
        public static final String postCreateNotepad =
                "/notepad/createNotepad";
        public static final String patchNotepad =
                "/notepad/{notepadSeq}";
    }

    /**
     * TODO 테스트 필요함
     *
     * @param session
     * @param notepadSeq
     * @return
     */
    @GetMapping(HandleUrl.getNotepad)
    public ResponseEntity<GetNotepadResponse> getNotepadById(
            HttpSession session,
            @PathVariable("notepadSeq") String notepadSeq) {
        // session 정보를 가져옴
        LogInController.LoginSessionInformation sessionInfo =
                getSessionInfo(session);
        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
        if (validSession(session, sessionInfo)) {
            return ResponseEntity.badRequest().build();
        }

        GetNotepadResponse responseBody = notepadService.getNotepad_ByNotepadIdAndStudentId(
                Long.valueOf(notepadSeq), sessionInfo.studentId()
        );

        log.trace("responseBody -> {}", responseBody);

        if (responseBody == null) {
            return ResponseEntity.badRequest().body(
                    GetNotepadResponse.builder()
                            .content("responseBody 가 null 입니다").build()
            );
        }

        return ResponseEntity.ok(responseBody);
    }

    /**
     * TODO 테스트 필요함
     *
     * @param session
     * @return
     */
    @GetMapping(HandleUrl.getNotepadList)
    public ResponseEntity<List<GetNotepadResponse>> getNotepadById(
            HttpSession session) {
        // session 정보를 가져옴
        LogInController.LoginSessionInformation sessionInfo =
                getSessionInfo(session);
        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
        if (validSession(session, sessionInfo)) {
            return ResponseEntity.badRequest().build();
        }

        List<GetNotepadResponse> responseBodyList = notepadService.getNotepadList_ByNotepadSeqAndStudentId(
                sessionInfo.studentId()
        );

        log.trace("responseBodyList -> {}", responseBodyList);


        // List 로 넘겨줘야돼서 조금 복잡해졌지만.. 전반적인 내용은 비슷함
        if (responseBodyList == null) {
            GetNotepadResponse response = GetNotepadResponse.builder()
                    .content("responseBodyList 가 null 입니다").build();
            List<GetNotepadResponse> responseList = new ArrayList<>();
            responseList.add(response);
            return ResponseEntity.badRequest().body(
                    responseList
            );
        }

        return ResponseEntity.ok(responseBodyList);
    }

    /**
     * TODO 테스트 필요함
     *
     * @param session
     * @param body
     * @return
     */
    @PostMapping(HandleUrl.postCreateNotepad)
    public ResponseEntity<CreateNotepadResponse> postCreateNotepad(
            HttpSession session,
            @RequestBody CreateNotepadRequest body
    ) {
        // session 정보를 가져옴
        LogInController.LoginSessionInformation sessionInfo =
                getSessionInfo(session);
        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
        if (validSession(session, sessionInfo)) {
            return ResponseEntity.badRequest().build();
        }

        // 유효한 유저인지 검사가 끝났으면, notepad 를 생성함!
        CreateNotepadResponse responseBody = notepadService.createNotepad(
                body.content(), sessionInfo.studentId());

        return ResponseEntity.ok(responseBody);
    }

    @Builder
    public record CreateNotepadRequest(
            String content
    ) {

    }


    /**
     * TODO 테스트 필요함!
     * @param session
     * @param notepadSeq
     * @param body
     * @return
     */
    @PatchMapping(HandleUrl.patchNotepad)
    public ResponseEntity updateNotepad(
            HttpSession session,
            @PathVariable
//                    ("notepadSeq") 없으면 자동으로 변수명으로 선언!
            String notepadSeq,
            @RequestBody @Valid UpdateNotepadRequest body
    ) {
        // session 정보를 가져옴
        LogInController.LoginSessionInformation sessionInfo =
                getSessionInfo(session);
        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
        if (validSession(session, sessionInfo)) {
            return ResponseEntity.badRequest().build();
        }

        log.trace("notepadSeq -> {}", notepadSeq);
        log.trace("request Body -> {}", body);

        CreateNotepadResponse responseBody =
                notepadService.updateNotepad(
                        Long.valueOf(notepadSeq),
                        body.content,
                        sessionInfo.studentId()
                );

        return ResponseEntity.ok(responseBody);
    }


    /**
     * TODO 테스트 필요함!
     * @param session
     * @param notepadSeq
     * @param body
     * @return
     */
    @DeleteMapping(HandleUrl.patchNotepad)
    public ResponseEntity deleteNotepad(
            HttpSession session,
            @PathVariable
//                    ("notepadSeq") 없으면 자동으로 변수명으로 선언!
            String notepadSeq,
            @RequestBody @Valid UpdateNotepadRequest body
    ) {
        // session 정보를 가져옴
        LogInController.LoginSessionInformation sessionInfo =
                getSessionInfo(session);
        // 유효한 세션이 아니면 HttpStatus.BAD_REQUEST return!!
        if (validSession(session, sessionInfo)) {
            return ResponseEntity.badRequest().build();
        }

        log.trace("notepadSeq -> {}", notepadSeq);
        log.trace("request Body -> {}", body);

        CreateNotepadResponse responseBody =
                notepadService.updateNotepad(
                        Long.valueOf(notepadSeq),
                        body.content,
                        sessionInfo.studentId()
                );

        return ResponseEntity.ok(responseBody);
    }

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
        return session.isNew() || sessionInfo == null;
    }

    @Builder
    public record UpdateNotepadRequest(
            Long notepadSeq,
            String content
    ) {
    }
}
