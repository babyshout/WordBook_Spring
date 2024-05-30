package kopo.data.wordbook.app.student.repository.entity;

import jakarta.persistence.*;
import kopo.data.wordbook.app.student.dto.StudentDTO;
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
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "STUDENT")
// @CreatedDate, @LastModifiedDate 작동을 위해 추가
// @link https://wildeveloperetrain.tistory.com/76
@EntityListeners(AuditingEntityListener.class)
public class StudentEntity {

    @Id
    @Column(name = "STUDENT_ID", length = 20, nullable = false)
    String studentId;
    @Column(name = "PASSWORD", length = 100, nullable = false)
    @NonNull
    String password;

    @Column(name = "EMAIL", length = 100, nullable = false)
    String email;
    @Column(name = "NAME", length = 20, nullable = false)
    String name;


    @Column(name = "REG_ID", length = 20, nullable = false)
    String regId;
    @Column(name = "REG_DATE")
    @CreatedDate
    LocalDate regDate;
    @Column(name = "CHANGER_ID", length = 20, nullable = false)
    String changerId;
    @Column(name = "CHANGER_DATE")
    @LastModifiedDate
    LocalDate changerDate;

    public static StudentEntity of(StudentDTO pDTO) {
        return StudentEntity.builder()
                .studentId(pDTO.studentId())
                .password(pDTO.password())
                .email(pDTO.email())
                .name(pDTO.name())
                .regId(pDTO.regId())
                .regDate(pDTO.regDate())
                .changerId(pDTO.changerId())
                .changerDate(pDTO.changerDate()).build();
    }
}
