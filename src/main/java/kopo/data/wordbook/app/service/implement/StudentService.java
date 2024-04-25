package kopo.data.wordbook.app.service.implement;

import kopo.data.wordbook.app.controller.response.LoginResponseData;
import kopo.data.wordbook.app.dto.MsgDTO;
import kopo.data.wordbook.app.dto.StudentDTO;
import kopo.data.wordbook.app.repository.StudentRepository;
import kopo.data.wordbook.app.repository.entity.StudentEntity;
import kopo.data.wordbook.app.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;

    @Override
    public LoginResponseData getLogin(String studentId, String password) {
        Optional<StudentEntity> rEntity = Optional.ofNullable(studentRepository.findByStudentIdAndPassword(studentId, password));

        LoginResponseData rData = null;

        if (rEntity.isEmpty()) {
            return rData;
        }
        return rData;
    }

    @Transactional
    @Override
    public MsgDTO createStudent(StudentDTO pDTO) {

        StudentEntity pEntity = StudentEntity.of(pDTO);

        // FIXME 쿼리 최적화 가능
        if (studentRepository.existsById(pDTO.studentId())) {
            return MsgDTO.builder()
                    .result(false)
                    .message("Id 가 이미 있습니다!!\n" +
                            "로그인 처리 안됨!!").build();
        }

        StudentEntity rEntity = studentRepository.save(pEntity);

        log.info("rEntity : " + rEntity);


        return MsgDTO.builder()
                .message("회원생성 성공!!")
                .result(true).build();
    }

    @Override
    public String getStudentId(String studentName, String email) {
        return null;
    }
}
