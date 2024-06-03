package kopo.data.wordbook.app.notepad.repository;

import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@DataJpaTest
public class NotepadRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    /**
     * target repository!!
     */
    @Autowired
    NotepadRepository notepadRepository;

    StudentEntity registerStudent;
    StudentEntity savedEntity;

    @BeforeEach
    void setUp() {
        LocalDate now = LocalDate.now();
        registerStudent =
                StudentEntity.builder()
                        .studentId("register")
                        .email("register@email.com")
                        .regId("register")
                        .changerId("register")
                        .name("registerName")
                        .password("registerPassword")
                        .build();

        savedEntity = studentRepository.save(registerStudent);

        log.info("savedStudentEntity -> {}", savedEntity);
        log.trace("registerStudent -> {}", registerStudent);

        this.assertStudentEntity(savedEntity, registerStudent, now);

//        Assertions.assertThat(savedEntity.getStudentId())
//                .isEqualTo(registerStudent.getStudentId());
//        Assertions.assertThat(savedEntity.getEmail())
//                .isEqualTo(registerStudent.getEmail());
//        Assertions.assertThat(savedEntity.getRegId())
//                .isEqualTo(registerStudent.getRegId());
//        Assertions.assertThat(savedEntity.getChangerId())
//                .isEqualTo(registerStudent.getChangerId());
//        Assertions.assertThat(savedEntity.getName())
//                .isEqualTo(registerStudent.getName());
//        Assertions.assertThat(savedEntity.getPassword())
//                .isEqualTo(registerStudent.getPassword());
//
//        Assertions.assertThat(savedEntity.getRegDate())
//                .isAfterOrEqualTo(now);
//        Assertions.assertThat(savedEntity.getChangerDate())
//                .isAfterOrEqualTo(now);

//        Assertions.assertThat(savedEntity).isEqualTo(registerStudent);
    }

    private void assertStudentEntity(
            StudentEntity savedEntity,
            StudentEntity registerStudent,
            LocalDate now
    ) {
        log.error("assertStudentEntity() is running");
        Assertions.assertThat(savedEntity.getStudentId())
                .isEqualTo(registerStudent.getStudentId());
        Assertions.assertThat(savedEntity.getEmail())
                .isEqualTo(registerStudent.getEmail());
        Assertions.assertThat(savedEntity.getRegId())
                .isEqualTo(registerStudent.getRegId());
        Assertions.assertThat(savedEntity.getChangerId())
                .isEqualTo(registerStudent.getChangerId());
        Assertions.assertThat(savedEntity.getName())
                .isEqualTo(registerStudent.getName());
        Assertions.assertThat(savedEntity.getPassword())
                .isEqualTo(registerStudent.getPassword());

        Assertions.assertThat(savedEntity.getRegDate())
                .isAfterOrEqualTo(now);
        Assertions.assertThat(savedEntity.getChangerDate())
                .isAfterOrEqualTo(now);
    }


    @Test
    @DisplayName("studentRepository, notepadRepository 가 null 이 아님!!")
    public void studentRepositoryAndNotepadRepository_isNotNull() {
        Assertions.assertThat(studentRepository).isNotNull();
        Assertions.assertThat(notepadRepository).isNotNull();
    }

    @Test
    @DisplayName("Notepad 가 제대로 save 됨!!")
    public void notepadSaveFirst() throws Exception {
        StudentEntity registerEntity =
                studentRepository.findById(registerStudent.getStudentId())
                        .orElseThrow(() -> new Exception("getStudentId() is NULL"));

        NotepadEntity notepadEntity =
                NotepadEntity.builder()
                        .content("notepad Contents")
                        .regStudent(registerEntity)
                        .chgStudent(registerEntity)
                        .build();

        NotepadEntity saveResult = notepadRepository.save(notepadEntity);

        log.trace("saveResult -> {}", saveResult);

        Assertions.assertThat(saveResult).isEqualTo(notepadEntity);
    }

    @Test
    @DisplayName("notepad 여러개가 저장되어있는게 잘 불러와짐")
    public void findByRegStudentAtNotepad() throws Exception {
        // given
//        StudentEntity registerEntity =
//                studentRepository.findById(registerStudent.getStudentId())
//                        .orElseThrow(() -> new Exception(
//                                "registerStudent.getStudentId() is NULL"
//                        ));
        StudentEntity registerEntity =
                studentRepository.findById(registerStudent.getStudentId())
                        .orElseThrow();
        StudentEntity registerEntity2 =
                studentRepository.findById(registerStudent.getStudentId())
                        .orElseThrow();

//        Assertions.assertThat(registerEntity).

        log.error("register == this.register -> {}" , registerEntity == this.registerStudent);
        log.error("register == this.register -> {}" , registerEntity == registerEntity2);
        log.error("register == this.register -> {}" , registerEntity == this.registerStudent);

        NotepadEntity notepadEntity1 = getNotepadEntity(
                "notepad content 1",
                registerEntity,
                registerEntity
        );
        NotepadEntity notepadEntity2 = getNotepadEntity(
                "notepad content 2",
                registerEntity,
                registerEntity
        );
        NotepadEntity notepadEntity3 = getNotepadEntity(
                "notepad content 3",
                registerEntity,
                registerEntity
        );

        log.trace("notepadEntity 1 -> {}", notepadEntity1);
        log.trace("notepadEntity 2 -> {}", notepadEntity2);
        log.trace("notepadEntity 3 -> {}", notepadEntity3);

        NotepadEntity savedNotepad1 = notepadRepository.save(notepadEntity1);
        NotepadEntity savedNotepad2 = notepadRepository.save(notepadEntity2);
        NotepadEntity savedNotepad3 = notepadRepository.save(notepadEntity3);

        log.trace("savedNotepad 1 -> {}", savedNotepad1);
        log.trace("savedNotepad 2 -> {}", savedNotepad2);
        log.trace("savedNotepad 3 -> {}", savedNotepad3);

        log.trace("registerEntity -> {}", registerEntity);

//        List<NotepadEntity> list = Optional.ofNullable(
//                notepadRepository.findAllByRegStudent(registerEntity)
//        ).orElseThrow();
        List<NotepadEntity> list =
                notepadRepository.findAllByRegStudent(registerEntity);
        log.info(list.size()+"");

//        list = notepadRepository.findAll();

//        NotepadEntity n1 = list.get(1);
//        log.error("n1.getRegStudent == studentEntity -> {}", n1.getRegStudent() == registerEntity);

//        log.error(notepadRepository.findByNotepadSeqAndRegStudent(
//                1L, StudentEntity.builder().studentId("register")
//                        .password("registerPassword").build()
//        ).toString());

//        list = notepadRepository.findAllByRegStudent_StudentId(registerEntity.getStudentId());
//
//        NotepadEntity n2 = list.get(1);
//        log.error("n2.getRegStudent == studentEntity -> {}", n2.getRegStudent() == registerEntity);


        log.info(list.size()+"");
        list.forEach(notepadEntity -> log.trace("notepadEntity : {}", notepadEntity));

        Assertions.assertThat(list.contains(savedNotepad1)).isTrue();
        Assertions.assertThat(list.contains(savedNotepad2)).isTrue();
        Assertions.assertThat(list.contains(savedNotepad3)).isTrue();

    }

    @AfterEach
    void tearDown() {
        // 하고싶은데 안됨..
//        notepadRepository.deleteAll();
//        studentRepository.deleteAll();
    }

    private NotepadEntity getNotepadEntity(
            String content,
            StudentEntity regStudent,
            StudentEntity chgStudent
    ) {

        return NotepadEntity.builder()
                .content(content)
                .regStudent(regStudent)
                .chgStudent(chgStudent)
                .build();
    }

}


