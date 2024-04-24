package kopo.data.wordbook.app.service;


import kopo.data.wordbook.app.dto.StudentDTO;

public interface IStudentService {
    int getLogin(String studentId, String password);

    int postSignUp(StudentDTO pDTO);

    String getStudentId(String studentName, String email);
}
