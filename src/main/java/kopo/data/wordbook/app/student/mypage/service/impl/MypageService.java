package kopo.data.wordbook.app.student.mypage.service.impl;

import kopo.data.wordbook.app.student.mypage.controller.request.PatchStudentInfoRequest;
import kopo.data.wordbook.app.student.mypage.response.EmailAuthCodeResponse;
import kopo.data.wordbook.app.student.mypage.response.StudentInfo;
import kopo.data.wordbook.app.student.mypage.service.IMypageService;
import kopo.data.wordbook.app.student.repository.StudentRepository;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.common.mail.IMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageService implements IMypageService {

    private final StudentRepository studentRepository;

    private final IMailService mailService;

    @Override
    public StudentInfo getStudentInfoByRepository(String studentId) {
        Optional<StudentEntity> optional = studentRepository.findById(studentId);

        if (optional.isEmpty()) {
            log.warn("studentEntity 가 비어있음");
            throw new RuntimeException("studentEntity 가 비어있음!!");
        }

        log.trace("studentEntity -> {}", optional.get());

        return StudentInfo.of(optional.get());
    }

    @Override
    public EmailAuthCodeResponse getEmailAuthCode(String email) {

        String code = randomIntegerCodeGenerator();
        String title = "이메일 인증코드";
        String contents = "회원님의 인증코드는 [" + code + "] 입니다.";

        mailService.doSendMail(email,
                title,
                contents);
        return EmailAuthCodeResponse.builder()
                .authCode(code)
                .build();
    }

    @Override
    public Boolean patchStudentInfo(PatchStudentInfoRequest request, String studentId, String authCodeBySession) {
        Optional<StudentEntity> byId = studentRepository.findById(studentId);

        if (!request.emailAuthCode().equals(request.emailAuthCodeByServer())) {
            throw new RuntimeException("request.emailAuthCode 와 request.emailAuthCodeByServer 가 다름!");
        }
        if (!authCodeBySession.equals(request.emailAuthCodeByServer())) {
            throw new RuntimeException("authCodeBySession 와 request.emailAuthCodeByServer 가 다름!");
        }
        // 위에 두개 조건으로 인해.. 아래는 항상 참이거나 거짓..
//        if (!authCodeBySession.equals(request.emailAuthCode())) {
//            throw new RuntimeException("authCodeBySession 와 request.emailAuthCode 가 다름!");
//        }

        if (byId.isEmpty()) {
            throw new RuntimeException("patchStudentInfo 에서 studentRepository.findById 실패!!");
        }
        StudentEntity studentById = byId.get();

        StudentEntity toSaveStudent = StudentEntity.builder()
                .studentId(studentById.getStudentId())
                .name(request.name())
                .email(request.email())
                .build();

        log.trace("studentById -> {}", studentById);
        log.trace("toSaveStudent -> {}", toSaveStudent);

        StudentEntity savedStudent = studentRepository.save(toSaveStudent);
        log.trace("savedStudent -> {}", savedStudent);

        log.error("log 한번 더 찍음!!");
        log.trace("studentById -> {}", studentById);
        log.trace("toSaveStudent -> {}", toSaveStudent);



        return null;
    }


    private String randomIntegerCodeGenerator() {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            randomNumber.append(random.nextInt(10));
        }

        return randomNumber.toString();
    }
}
