package kopo.data.wordbook.app.service.implement;

import kopo.data.wordbook.app.dto.StudentDTO;
import kopo.data.wordbook.app.repository.StudentRepository;
import kopo.data.wordbook.app.repository.entity.StudentEntity;
import kopo.data.wordbook.app.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;

    @Override
    public int getLogin(String studentId, String password) {
        Optional<StudentEntity> rEntity = studentRepository.findByStudentIdAndPassword(studentId, password);

        if (rEntity.isEmpty()) {
            return 0;
        }
        return 1;
    }

    @Override
    public int postSignUp(StudentDTO pDTO) {
        StudentEntity pEntity = StudentEntity.builder()

                .build();
        StudentEntity rEntity = studentRepository.save(pEntity);
        return 0;
    }

    @Override
    public String getStudentId(String studentName, String email) {
        return null;
    }
}
