package kopo.data.wordbook.app.student.mypage.response;

import kopo.data.wordbook.app.student.repository.entity.StudentEntity;
import kopo.data.wordbook.common.util.EncryptUtil;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StudentInfo(
        String studentId,
        String password,
        String email,
        String name,
        String regId,
        LocalDate regDate,
        String changerId,
        LocalDate changerDate
) {
   static public StudentInfo of(StudentEntity entity) {
        return StudentInfo.builder()
                .studentId(entity.getStudentId())
                .password(entity.getPassword())
                .email(EncryptUtil.decAES128CBC(entity.getEmail()))
                .name(entity.getName())
                .regId(entity.getRegId())
                .regDate(entity.getRegDate())
                .changerId(entity.getChangerId())
                .changerDate(entity.getChangerDate())
                .build();
    }
}
