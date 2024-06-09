package kopo.data.wordbook.app.student.mypage.service;

import kopo.data.wordbook.app.student.mypage.controller.request.PatchStudentInfoRequest;
import kopo.data.wordbook.app.student.mypage.controller.request.PatchStudentPasswordRequest;
import kopo.data.wordbook.app.student.mypage.response.EmailAuthCodeResponse;
import kopo.data.wordbook.app.student.mypage.response.StudentInfo;

public interface IMypageService {
    StudentInfo getStudentInfoByRepository(String studentId);

    EmailAuthCodeResponse getEmailAuthCode(String email);

    Boolean patchStudentInfo(PatchStudentInfoRequest request, String studentId, String authCodeBySession);

    Boolean patchStudentPassword(PatchStudentPasswordRequest request, String studentId);
}
