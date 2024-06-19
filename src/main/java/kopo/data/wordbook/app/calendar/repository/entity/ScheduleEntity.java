package kopo.data.wordbook.app.calendar.repository.entity;

import jakarta.persistence.*;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity
@Table
// @CreatedDate, @LastModifiedDate 작동을 위해 추가
// @link https://wildeveloperetrain.tistory.com/76
@EntityListeners(AuditingEntityListener.class)
@IdClass(ScheduleEntityId.class)
public class ScheduleEntity {
    @Id
    @GeneratedValue
    private Long scheduleSeq;

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private StudentEntity studentId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate start;

    private LocalDate end;
}
