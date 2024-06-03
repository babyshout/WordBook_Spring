package kopo.data.wordbook.app.notepad.repository.entity;

import jakarta.persistence.*;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
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
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "NOTEPAD")
// @CreatedDate, @LastModifiedDate 작동을 위해 추가
// @link https://wildeveloperetrain.tistory.com/76
@EntityListeners(AuditingEntityListener.class)
public class NotepadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NOTEPAD_SEQ")
    private Long notepadSeq;

    @Column(name = "CONTENT", length = 4000)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
//            name = "REG_STUDENT_ID",
//            nullable = false,
            updatable = false,
            insertable = false
    )
    private StudentEntity regStudent;

    @CreatedDate
    @Column(name = "REG_DATE")
    private LocalDate regDate;

    @ManyToOne
    @JoinColumn(
//            name = "CHG_STUDENT_ID"
//            nullable = false
    )
    private StudentEntity chgStudent;

    @LastModifiedDate
    @Column(name = "CHG_DATE")
    private LocalDate chgDate;

}
