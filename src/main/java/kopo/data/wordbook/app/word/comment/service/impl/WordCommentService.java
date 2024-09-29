package kopo.data.wordbook.app.word.comment.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntity;
import kopo.data.wordbook.app.word.comment.service.IWordCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WordCommentService implements IWordCommentService {
    /**
     * 댓글 리스트를 가져옴
     *
     * @param wordName    단어이름
     * @param sessionInfo 세션정보
     * @param session     {@link HttpSession}
     * @return 댓글 리스트 {@link List < CommentEntity >}
     */
    @Override
    public List<CommentEntity> getCommentList(String wordName, LogInController.LoginSessionInformation sessionInfo, HttpSession session) {

        return null;
    }
}
