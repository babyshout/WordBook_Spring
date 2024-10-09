package kopo.data.wordbook.app.word.comment.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.calendar.exception.ScheduleException;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.comment.repository.CommentRepository;
import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntity;
import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntityId;
import kopo.data.wordbook.app.word.comment.service.IWordCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WordCommentService implements IWordCommentService {
    /**
     * {@link CommentEntity} 를 관리하는 {@link org.springframework.data.jpa.repository.JpaRepository}
     */
    private final CommentRepository commentRepository;

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

        return commentRepository.findAllByWordName(wordName);
    }

    /**
     * content 를 통해 {@link CommentEntity} 생성
     *
     * @param content   댓글내용
     * @param wordName  댓글달 단어 이름 (단어 pk)
     * @param studentId 댓글 달 유저 이름
     * @return save 한 내용
     */
    @Override
    public CommentEntity createComment(String content, String wordName, String studentId) {

        CommentEntity build = CommentEntity.builder()
                .content(content)
                .regId(studentId)
                .wordName(wordName).build();

        log.trace("build : {}", build);

        CommentEntity saved = commentRepository.save(build);

        log.trace("saved : {}", saved);

        return saved;
    }

    /**
     * wordName, wordCommentSeq 를 통해 해당 댓글을 수정함
     *
     * @param content
     * @param wordName
     * @param wordCommentSeq
     * @param studentId
     * @return
     */
    @Override
    public CommentEntity updateComment(String content, String wordName, String wordCommentSeq, String studentId) {

        CommentEntityId entityId = CommentEntityId.builder()
                .wordCommentSeq(Long.valueOf(wordCommentSeq))
                .wordName(wordName).build();

        log.trace("entity Id : {}", entityId);

        CommentEntity entity = commentRepository.findById(entityId).orElseThrow(
                () -> new ScheduleException("student 가 존재하지 않음", HttpStatus.UNAUTHORIZED)
        );

        log.trace("entity : {}", entity);

        entity.setContent(content);

        CommentEntity saved = commentRepository.save(entity);

        log.trace("saved : {}", saved);
        return saved;
    }

    /**
     * wordName, wordCommentSeq 를 통해 해당 댓글을 삭제함
     *
     * @param wordName
     * @param wordCommentSeq
     * @param studentId
     * @return
     */
    @Override
    public CommentEntity deleteComment(String wordName, String wordCommentSeq, String studentId) {

        CommentEntityId entityId = CommentEntityId.builder()
                .wordCommentSeq(Long.valueOf(wordCommentSeq))
                .wordName(wordName).build();

        CommentEntity entity = commentRepository.findById(entityId).orElseThrow(
                () -> new ScheduleException("student 가 존재하지 않음", HttpStatus.UNAUTHORIZED)
        );

        commentRepository.delete(entity);

//        commentRepository.deleteById(entityId);

        return entity;
    }
}
