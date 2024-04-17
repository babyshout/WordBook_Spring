package kopo.data.wordbook.app.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Slf4j
//@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "STUDENT")
public class StudentEntity

 {
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
        @Column(name = "CHGANGER_ID", length = 20, nullable = false)
        String changerId;
        @Column(name = "CHANGER_DATE")
        LocalDate changerDate;
}
