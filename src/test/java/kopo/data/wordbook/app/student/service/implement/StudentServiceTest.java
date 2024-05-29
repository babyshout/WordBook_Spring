package kopo.data.wordbook.app.student.service.implement;

import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService subject;

    @Mock
    private StudentRepository studentRepository;


    String name = "testName";
    String email = "test@email";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLogin() {
    }

    @Test
    void createStudent() {
    }

    @Test
    void getStudentIdList() {
        // given
        Mockito.doReturn(getOptionalStudentEntityList())
                .when(studentRepository)
                .findAllByNameAndEmail(name, email);
        List<String> expectIdList = new ArrayList<>();
        expectIdList.add("testId1");
        expectIdList.add("testId2");

        // when
        final List<String> idList = subject.getStudentIdList(name, email);

        // then
        Assertions.assertThat(idList).isNotNull();
        Assertions.assertThat(idList).isEqualTo(expectIdList);
    }

    private final Optional<List<StudentEntity>> getOptionalStudentEntityList() {
        List<StudentEntity> studentEntityList = new ArrayList<>();
        studentEntityList.add(
                StudentEntity.builder()
                        .studentId("testId1")
                        .password("1234")
                        .build()
        );
        studentEntityList.add(
                StudentEntity.builder()
                        .studentId("testId2")
                        .password("1234")
                        .build()
        );
        return Optional.of(studentEntityList);
    }

    @Test
    void resetPasswordForId() {
    }

    @Test
    void resetPasswordGeneratorTest() {
        Random random = new Random();
        System.out.println(random.ints(10));
//        System.out.println(random.ints(10, 15).boxed().collect(Collectors.toList()));
        System.out.println(random.ints(10, 15, 20));
        System.out.println(random.nextInt(10));

        for (int i = 0; i < 1000; i++)  {
            StringBuilder randomNumber = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                randomNumber.append(random.nextInt(10));
            }
//            System.out.println(randomNumber);

//            Assertions.assertThat(Integer.parseInt(randomNumber)).isBetween(100000, 1000000);
            Assertions.assertThat(randomNumber.toString().length()).isEqualTo(6);
        }
    }
}