package kopo.data.wordbook.app.notepad.repository;

import kopo.data.wordbook.app.notepad.repository.entity.NotepadEntity;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotepadRepository extends JpaRepository<NotepadEntity, Long> {

    List<NotepadEntity> findAllByRegStudent(StudentEntity regStudent);

    List<NotepadEntity> findAllByChgStudent(StudentEntity chgStudent);

    //    List<NotepadEntity> findAllByRegStudent
//        List<NotepadEntity> findAllByRegStudent_StudentId(String regStudent_studentId);
    List<NotepadEntity> findAllByRegStudentIs(StudentEntity regStudent);

    List<NotepadEntity> findAllByRegStudentEquals(StudentEntity regStudent);

    List<NotepadEntity> findAllByRegStudent_StudentId(String studentId);

    Optional<NotepadEntity> findByNotepadSeqAndRegStudent(
            Long notepadSeq, StudentEntity regStudent
    );

}
