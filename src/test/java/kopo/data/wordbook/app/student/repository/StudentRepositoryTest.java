package kopo.data.wordbook.app.student.repository;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    StudentRepository repository;

    StudentEntity entity;


    /**
     * 처음 시작할때 필요한 변수들 선언...
     */
    String studentId = "testId";
    String name = "testName";
    String password = "testPassword";
    String email = "test@email";
    String regId = "testId";
    LocalDate regDate = LocalDate.now();
    String changerId = "testId";
    LocalDate changerDate = LocalDate.now();


    @BeforeEach
    void setUp() {
        entity = StudentEntity.builder()
                .studentId(studentId)
                .name(name)
                .password(password)
                .email(email)
                .regId(studentId)
                .regDate(regDate)
                .changerId(studentId)
                .changerDate(regDate)
                .build();


        repository.save(entity);

        assertThat(
                repository.findById("testId").get()
        ).isEqualTo(entity);
    }

    @AfterEach
    void tearDown() {
        repository.delete(repository.findById(studentId).get());

        assertThat(
                repository.findById("testId")
        ).isEmpty();
    }

    @Test
    void findByStudentIdAndPassword() {
    }

    @Test
    void findAllByNameAndEmail() {
    }

    @Test
    void findByIdAndNameAndEmail() {

    }
}