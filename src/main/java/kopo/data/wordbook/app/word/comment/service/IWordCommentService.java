package kopo.data.wordbook.app.word.comment.service;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntity;

import java.util.List;

/**
 * 각 단어별 댓글을 관리
 */
public interface IWordCommentService {
    /**
     * 댓글 리스트를 가져옴
     * @param wordName 단어이름
     * @param sessionInfo 세션정보
     * @param session {@link HttpSession}
     * @return 댓글 리스트 {@link List<CommentEntity>}
     */
    List<CommentEntity> getCommentList(String wordName, LogInController.LoginSessionInformation sessionInfo, HttpSession session);
}
