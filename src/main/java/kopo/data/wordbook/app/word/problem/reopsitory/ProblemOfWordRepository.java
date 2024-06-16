package kopo.data.wordbook.app.word.problem.reopsitory;

import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.word.problem.reopsitory.entity.ProblemOfWordEntity;
import kopo.data.wordbook.app.word.problem.reopsitory.entity.ProblemOfWordEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemOfWordRepository extends JpaRepository<ProblemOfWordEntity, ProblemOfWordEntityId> {
    List<ProblemOfWordEntity> findAllByMywordEntity_Student(StudentEntity mywordEntity_student);
}
