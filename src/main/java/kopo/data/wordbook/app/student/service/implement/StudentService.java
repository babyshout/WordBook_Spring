package kopo.data.wordbook.app.student.service.implement;

import kopo.data.wordbook.app.student.constants.StudentErrorResult;
import kopo.data.wordbook.app.student.exception.StudentException;
import kopo.data.wordbook.app.student.controller.response.LoginResponseData;
import kopo.data.wordbook.app.student.controller.response.ResetPasswordForIdResult;
import kopo.data.wordbook.app.student.dto.MsgDTO;
import kopo.data.wordbook.app.student.dto.StudentDTO;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.app.student.service.IStudentService;
import kopo.data.wordbook.common.mail.IMailService;
import kopo.data.wordbook.common.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final IMailService mailService;

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

    public StudentEntity getStudentById(String studentId) {
        Optional<StudentEntity> entityOptional = studentRepository.findById(studentId);

        if (entityOptional.isEmpty()) {
            throw new StudentException(StudentErrorResult.NO_ENTITY);
        }

        return entityOptional.get();
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

        if (optionalResultList.isEmpty() || optionalResultList.get().size() == 0) {
            log.warn("getStudentId by name and email got NOTHING!!!1");
            return null;
        }

        List<String> returningList = new ArrayList<>();
        optionalResultList.get().forEach(studentEntity -> log.error(studentEntity.getStudentId()));
        optionalResultList.get().forEach(studentEntity -> returningList.add(studentEntity.getStudentId()));
        returningList.stream().limit(2).forEach(value -> log.trace("value in returning List : " + value));


        return returningList;
    }

    public enum ResultMessage {
        SUCCESS_RESET_PASSWORD_FOR_ID("재설정 됐습니다");
        public final String resultMessage;

        ResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }

    }

    /**
     * reset 요청 들어왔을때 사용
     * 처리하는 service 로직
     *
     * @param studentId
     * @param name
     * @param email
     * @return
     */
    @Override
    @Transactional
    public ResetPasswordForIdResult resetPasswordForId(String studentId, String name, String email) {
        Optional<StudentEntity> rEntity = studentRepository.findByStudentIdAndNameAndEmail(studentId, name, email);

        // 해당 id 가 존재 안한다면 실패했다고 메세지를 보냄
        if (rEntity.isEmpty()) {
            return ResetPasswordForIdResult.builder()
                    .message("해당 아이디가 없습니다")
                    .isSuccess(false)
                    .build();
        }

        String randomPassword = randomPasswordGenerator();

        StudentEntity newEntityWithNewPassword =
                createNewStudentEntityWithNewPassword(randomPassword, rEntity.get());

        studentRepository.save(newEntityWithNewPassword);
        String toMail = EncryptUtil.decAES128CBC(newEntityWithNewPassword.getEmail());
        String title = "비밀번호 재설정";
        String contents = "비밀번호가 [" + randomPassword + "] 로 재설정 되었습니다.";
        mailService.doSendMail(toMail, title, contents);


//        return ResultMessage.SUCCESS_RESET_PASSWORD_FOR_ID.resultMessage;

        return ResetPasswordForIdResult.builder()
                .message("비밀번호 재설정 성공")
                .isSuccess(true)
                .build();
    }

    private StudentEntity createNewStudentEntityWithNewPassword(String newPassword, StudentEntity baseEntity) {
        return         StudentEntity.builder()
                .studentId(    baseEntity.getStudentId())
                .password(     EncryptUtil.encHashSHA256(newPassword))
                .email(        baseEntity.getEmail())
                .name(         baseEntity.getName())
                .regId(        baseEntity.getRegId())
                .regDate(      baseEntity.getRegDate())
                .changerId(    baseEntity.getChangerId())
                .changerDate(  baseEntity.getChangerDate())
                .build();
    }

    private String randomPasswordGenerator() {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            randomNumber.append(random.nextInt(10));
        }

        return randomNumber.toString();
    }
}
