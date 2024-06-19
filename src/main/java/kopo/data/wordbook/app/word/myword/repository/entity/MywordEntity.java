package kopo.data.wordbook.app.word.myword.repository.entity;


import jakarta.persistence.*;
import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.problem.reopsitory.entity.ProblemOfWordEntity;
import kopo.data.wordbook.app.word.repository.WordRepository;
import kopo.data.wordbook.app.word.repository.document.WordDocument;
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
import java.util.Set;


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

    public static final String RECENTLY_SEARCH_MYWORD =
            "RECENTLY_SEARCH";

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
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

    @CreatedDate
    private LocalDate regDate;
    @LastModifiedDate
    private LocalDate chgDate;



    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "mywordEntity"
    )
    List<ProblemOfWordEntity> problemOfWordEntityList = new ArrayList<>();



    /**
     * save 필요할것으로 보임..
     * NOTE 왠지는 모르나 save 안해도 저장됨..!
     * @param wordName 추가할 단어명
     * @param wordRepository 단어검색시 사용할 WordDocument 를 컨트롤하는 repository
     * @return wordName 이 추가된 {@link #wordNameList} 리턴함!!
     */
    public List<String> addWordNameToWordNameList(String wordName, WordRepository wordRepository) {
        log.trace("wordName -> {}", wordName);
        if (this.wordNameList == null) {
            log.warn("this.wordNameList == null!!");
            this.wordNameList = new ArrayList<>();
        }

        WordDocument wordDocument = wordRepository.findByWordName(wordName).orElseThrow(
                () -> new RuntimeException("mywordEntity 에 wordName 추가도중, mongoDB 에서 findByWordName 실패.."));

        log.trace("wordDocument findByWordName -> {}", wordDocument);

        if (wordNameList.contains(wordName)) {
            log.warn("wordName 이 이미 wordNameList 에 포함됨..");
        }
        wordNameList.add(wordName);

        return this.wordNameList;
    }

    public Set<String> wordNameListToSet() {
        Set<String> wordNameSet = Set.copyOf(wordNameList);
        log.trace("wordNameSet -> {}", wordNameSet);
        return wordNameSet;
    }
}
