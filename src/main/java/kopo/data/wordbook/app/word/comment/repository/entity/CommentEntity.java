package kopo.data.wordbook.app.word.comment.repository.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
// dynamic -> 어떤 쿼리 실행되는지.. 확인할대 편함
@DynamicInsert
@DynamicUpdate
@Entity
@Table
// @CreatedDate, @LastModifiedDate 작동을 위해 추가
// @link https://wildeveloperetrain.tistory.com/76
@EntityListeners(AuditingEntityListener.class)
@IdClass(CommentEntityId.class)
/**
 * 단어별 댓글을 저장
 */
public class CommentEntity {

    /**
     * 단어이름
     */
    @Id
    private String wordName;

    /**
     * 단어이름 - 댓글 별 시퀀스
     */
    @Id
    @GeneratedValue
    private Long wordCommentSeq;

    /**
     * 댓글내용
     */
    @Column(length = 2000)
    private String content;

    /**
     * 작성자 ID
     */
    private String regId;

    /**
     * 작성일
     */
    @CreatedDate
    private LocalDate regDate;
    /**
     * 수정일
     */
    @LastModifiedDate
    private LocalDate chgDate;
}
