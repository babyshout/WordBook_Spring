package kopo.data.wordbook.app.calendar.repository;

import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntity;
import kopo.data.wordbook.app.calendar.repository.entity.ScheduleEntityId;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, ScheduleEntityId> {
    List<ScheduleEntity> findAllByStudentId(StudentEntity studentId);

    Boolean existsDistinctByStudentIdAndStartAndTitle(
            StudentEntity studentId,
            LocalDate start,
            String title
    );
}
