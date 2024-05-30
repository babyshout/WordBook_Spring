package kopo.data.wordbook.app.student.service;


import kopo.data.wordbook.app.student.controller.response.LoginResponseData;
import kopo.data.wordbook.app.student.controller.response.ResetPasswordForIdResult;
import kopo.data.wordbook.app.student.controller.rest.SignupController;
import kopo.data.wordbook.app.student.dto.MsgDTO;
import kopo.data.wordbook.app.student.dto.StudentDTO;
import kopo.data.wordbook.app.student.repository.entity.StudentEntity;

import java.util.List;

public interface IStudentService {
    LoginResponseData getLogin(String studentId, String password);


    StudentEntity getStudentById(String studentId);

    MsgDTO createStudent(StudentDTO pDTO);

    List<String> getStudentIdList(String studentName, String email);

    /**
     * reset 요청 들어왔을때 사용
     * 처리하는 service 로직
     *
     * @param studentId
     * @param name
     * @param email
     * @return
     */
    ResetPasswordForIdResult resetPasswordForId(String studentId, String name, String email);

    SignupController.EmailVerificationCodeResult getEmailVerificationCode(String email);
}
