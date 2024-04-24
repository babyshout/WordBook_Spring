package kopo.data.wordbook.app.repository;

import kopo.data.wordbook.app.repository.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
    Optional<StudentEntity> findByStudentIdAndPassword(String studentId, String password);

    Optional<StudentEntity> findByNameAndEmail(String name, String email);

//    Optional<StudentEntity>

//    Optional<StudentEntity>


}
