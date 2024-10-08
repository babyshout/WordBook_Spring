package kopo.data.wordbook.app.word.comment.service.impl;

import jakarta.servlet.http.HttpSession;
import kopo.data.wordbook.app.student.controller.rest.LogInController;
import kopo.data.wordbook.app.word.comment.repository.CommentRepository;
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
}
