package kopo.data.wordbook.app.word.myword.repository;

import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MywordRepository extends JpaRepository<MywordEntity, MywordEntityId> {
    List<MywordEntity> findAllByStudentIs(StudentEntity student);

    Optional<MywordEntity> findByStudentAndMywordName(StudentEntity student, String mywordName);


}
