package kopo.data.wordbook.app.service;


import kopo.data.wordbook.app.controller.response.LoginResponseData;
import kopo.data.wordbook.app.dto.MsgDTO;
import kopo.data.wordbook.app.dto.StudentDTO;

import java.util.List;

public interface IStudentService {
    LoginResponseData getLogin(String studentId, String password);

    MsgDTO createStudent(StudentDTO pDTO);

    List<String> getStudentId(String studentName, String email);
}
