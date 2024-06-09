package kopo.data.wordbook.app.word.myword.repository.entity;


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
import java.util.ArrayList;
import java.util.List;


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
@IdClass(MywordEntityId.class)
public class MywordEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentEntity student;

    @Id
    private String mywordName;

    @ElementCollection
    @CollectionTable(
//            name = "something_simple_list",
//            joinColumns = {
//                    @JoinColumn(
//                            name = "something_id",
//                            referencedColumnName = "id")
//            }
    )
    @Setter
    private List<String> wordNameList = new ArrayList<>();

    @LastModifiedDate
    private LocalDate regDate;
    @CreatedDate
    private LocalDate chgDate;
}
