package kopo.data.wordbook.app.student.repository.entity;

import jakarta.persistence.*;
import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntity;
import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import kopo.data.wordbook.app.student.dto.StudentDTO;
import kopo.data.wordbook.app.student.social.repository.entity.SocialLoginEntity;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "STUDENT_ID", length = 100, nullable = false)
    String studentId;

    @Setter
    @Column(name = "PASSWORD", length = 100, nullable = false)
    @NonNull
    String password;

    @Setter
    @Column(name = "EMAIL", length = 100, nullable = false)
    String email;
    @Setter
    @Column(name = "NAME", length = 20, nullable = false)
    String name;


    @Column(name = "REG_ID", length = 100, nullable = false)
    String regId;
    @Column(name = "REG_DATE")
    @CreatedDate
    LocalDate regDate;
    @Column(name = "CHANGER_ID", length = 100, nullable = false)
    String changerId;
    @Column(name = "CHANGER_DATE")
    @LastModifiedDate
    LocalDate changerDate;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
//            fetch = FetchType.EAGER,
            mappedBy = "student"
    )
    List<MywordEntity> mywordEntityList = new ArrayList<>();

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "regStudent"
    )
    List<NotepadEntity> notepadEntityList = new ArrayList<>();

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "student"
    )
    List<SocialLoginEntity> socialLoginEntityList = new ArrayList<>();

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "studentId"
    )
    List<ScheduleEntity> scheduleEntityList = new ArrayList<>();

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
