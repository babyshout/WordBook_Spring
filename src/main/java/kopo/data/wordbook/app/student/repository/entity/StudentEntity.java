package kopo.data.wordbook.app.student.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kopo.data.wordbook.app.student.dto.StudentDTO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Slf4j
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "STUDENT")
@EqualsAndHashCode
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
    LocalDate regDate;
    @Column(name = "CHANGER_ID", length = 20, nullable = false)
    String changerId;
    @Column(name = "CHANGER_DATE")
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
