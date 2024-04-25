package kopo.data.wordbook.app.service;


import kopo.data.wordbook.app.controller.response.LoginResponseData;
import kopo.data.wordbook.app.dto.MsgDTO;
import kopo.data.wordbook.app.dto.StudentDTO;

public interface IStudentService {
    LoginResponseData getLogin(String studentId, String password);

    MsgDTO createStudent(StudentDTO pDTO);

    String getStudentId(String studentName, String email);
}
