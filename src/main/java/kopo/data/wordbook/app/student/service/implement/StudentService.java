package kopo.data.wordbook.app.student.service.implement;

import kopo.data.wordbook.app.student.controller.response.LoginResponseData;
import kopo.data.wordbook.app.student.dto.MsgDTO;
import kopo.data.wordbook.app.student.dto.StudentDTO;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.student.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public LoginResponseData getLogin(String studentId, String password) {
        Optional<StudentEntity> rEntity = Optional.ofNullable(studentRepository.findByStudentIdAndPassword(studentId, password));

        if (rEntity.isEmpty()) {
            return LoginResponseData.builder()
                    .isLogin(false).build();
        }


        return LoginResponseData.builder()
                .isLogin(true)
                .name(rEntity.get().getName())
                .studentId(rEntity.get().getStudentId()).build();
    }

    @Transactional()
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
    public List<String> getStudentIdList(String studentName, String email) {

        Optional<List<StudentEntity>> optionalResultList = studentRepository.findAllByNameAndEmail(studentName, email);

        if(optionalResultList.isEmpty()){
            log.warn("getStudentId by name and email got NOTHING!!!1");
            return null;
        }

        List<String> returningList = new ArrayList<>();
        optionalResultList.get().forEach(studentEntity -> returningList.add(studentEntity.getStudentId()));
        returningList.stream().limit(2).forEach(value -> log.trace("value in returning List : " + value));



        return returningList;
    }
}
