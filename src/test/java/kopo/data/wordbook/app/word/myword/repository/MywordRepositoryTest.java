package kopo.data.wordbook.app.word.myword.repository;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@RequiredArgsConstructor(
        // 이 어노테이션으로 인해 생기는 생성자에 @Autowired 를 추가하기 위해 사용
        onConstructor_ = {@Autowired}
)
@Slf4j
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MywordRepositoryTest {

    private final StudentRepository studentRepository;
    private final MywordRepository mywordRepository;

    private StudentEntity student1;
    private final String student1Id = "student1";
    private StudentEntity student2;
    private final String student2Id = "student2";

    private MywordEntity mywordEntity1OfStudent1;
    private List<String> wordNameListFormywordEntity1;


    @BeforeEach
    @Transactional
    void setUp() {
        // 테스트에 사용할 student1, student2
        StudentEntity student1BeforeSave = getStudentEntity(student1Id);
        StudentEntity student2BeforeSave = getStudentEntity(student2Id);
        student1 = studentRepository.save(student1BeforeSave);
        student2 = studentRepository.save(student2BeforeSave);

        log.trace("saved student1 -> {}", student1);
        log.trace("saved student2 -> {}", student2);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(student1).isNotNull();
            softAssertions.assertThat(student2).isNotNull();
        });

        // 테스트에 사용할 mywordEntity1OfStudent1
        mywordEntity1OfStudent1 = MywordEntity.builder()
                .student(student1)
                .mywordName("myword1Name")
                .build();

        log.trace("mywordEntity1 before save -> {}", mywordEntity1OfStudent1);
        mywordEntity1OfStudent1 = mywordRepository.save(mywordEntity1OfStudent1);
        log.trace("mywordEntity1 after save -> {}", mywordEntity1OfStudent1);


    }
    @Test
    @Transactional
    void mywordEntityOfStudent1IsNotNull() {
        StudentEntity student1AfterMyword1Save =
                studentRepository.findById(student1Id).get();

        log.trace("mywordEntity1 in student1?? -> {}", student1AfterMyword1Save.getMywordEntityList());
        Assertions.assertThat(student1AfterMyword1Save.getMywordEntityList()
        ).isNotNull();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllByStudentIs() {

        mywordRepository.findAllByStudentIs(student1);
    }

    @Test
    void findByStudentAndMywordName() {
    }

    private StudentEntity getStudentEntity(String studentId) {
        return StudentEntity.builder()
                .studentId(studentId)
                .password(studentId + "Password")
                .email(studentId + "@email.com")
                .name(studentId + "Name")
                .regId(studentId)
                .changerId(studentId)
                .build();
    }

    @Test
    void repositoriesAreNotNull() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(studentRepository).isNotNull();
            softAssertions.assertThat(mywordRepository).isNotNull();
        });
    }
}