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

    /**
     * content 를 통해 {@link CommentEntity} 생성
     * @param content 댓글내용
     * @param wordName 댓글달 단어 이름 (단어 pk)
     * @param studentId 댓글 달 유저 이름
     * @return save 한 내용
     */
    CommentEntity createComment(String content, String wordName, String studentId);

    /**
     * wordName, wordCommentSeq 를 통해 해당 댓글을 수정함
     * @param content
     * @param wordName
     * @param wordCommentSeq
     * @param studentId
     * @return
     */
    CommentEntity updateComment(String content, String wordName, String wordCommentSeq, String studentId);

    /**
     * wordName, wordCommentSeq 를 통해 해당 댓글을 삭제함
     * @param wordName
     * @param wordCommentSeq
     * @param studentId
     * @return
     */
    CommentEntity deleteComment(String wordName, String wordCommentSeq, String studentId);
}
