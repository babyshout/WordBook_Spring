package kopo.data.wordbook.app.repository;

import kopo.data.wordbook.app.repository.entity.StudentEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    StudentRepository repository;

    StudentEntity entity;

    LocalDate registeredDate;

    @BeforeEach
    void setUp() {
        registeredDate = LocalDate.now();

        entity = StudentEntity.builder()
                .studentId("testId")
                .name("testName")
                .password("testPassword")
                .email("test@email")
                .regId("testId")
                .regDate(registeredDate)
                .changerId("testId")
                .changerDate(registeredDate)
                .build();

        repository.save(entity);

        assertThat(
                repository.findById("testId").get()
        ).isEqualTo(entity);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByStudentIdAndPassword() {
    }

    @Test
    void findAllByNameAndEmail() {
    }
}