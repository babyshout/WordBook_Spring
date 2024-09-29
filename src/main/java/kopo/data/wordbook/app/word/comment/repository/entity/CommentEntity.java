package kopo.data.wordbook.app.word.comment.repository.entity;


import jakarta.persistence.*;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.problem.reopsitory.entity.ProblemOfWordEntityId;
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
@IdClass(ProblemOfWordEntityId.class)
public class CommentEntity {

    @Id
    private String wordName;

    @Id
    @GeneratedValue
    private Long wordCommentSeq;

    private String studentName;

    @CreatedDate
    private LocalDate regDate;
    @LastModifiedDate
    private LocalDate chgDate;
}
