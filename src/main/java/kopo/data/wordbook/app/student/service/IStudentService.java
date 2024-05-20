package kopo.data.wordbook.app.student.service;


import kopo.data.wordbook.app.student.controller.response.LoginResponseData;
import kopo.data.wordbook.app.student.dto.MsgDTO;
import kopo.data.wordbook.app.student.dto.StudentDTO;

import java.util.List;

public interface IStudentService {
    LoginResponseData getLogin(String studentId, String password);

    MsgDTO createStudent(StudentDTO pDTO);

    List<String> getStudentIdList(String studentName, String email);
}
