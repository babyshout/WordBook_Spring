package kopo.data.wordbook.app.student.repository;

import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
    StudentEntity findByStudentIdAndPassword(String studentId, String password);

    /**
     * 아이디 찾기에 활용
     * @param name Student 의 이름
     * @param email Student 의 이메일
     * @return 찾으면.. 해당 entity 를 전부 보내줌
     */
    Optional<List<StudentEntity>> findAllByNameAndEmail(String name, String email);

    Optional<StudentEntity> findByStudentIdAndNameAndEmail(String studentId, String name, String email);

//    Optional<StudentEntity>

//    Optional<StudentEntity>


}
