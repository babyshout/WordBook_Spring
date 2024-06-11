package kopo.data.wordbook.app.student.social.repository.entity;

import jakarta.persistence.*;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.student.social.repository.entity.constant.SocialLoginProvider;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@IdClass(SocialLoginEntityId.class)
public class SocialLoginEntity {

    @Id
    private String id_bySocialLoginProvider;

    @Id
    @Enumerated(EnumType.STRING)
    private SocialLoginProvider provider;

    @JoinColumn(
            nullable = false
    )
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private StudentEntity student;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;
}
