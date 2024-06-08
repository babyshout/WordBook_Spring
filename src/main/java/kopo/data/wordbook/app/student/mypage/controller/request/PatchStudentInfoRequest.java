package kopo.data.wordbook.app.student.mypage.controller.request;

import jakarta.validation.constraints.NotNull;

public record PatchStudentInfoRequest(
        @NotNull(message = "name 빠져있음")
        String name,
        @NotNull(message = "email 빠져있음")
        String email,
        @NotNull(message = "emailAuthCode 빠져있음")
        String emailAuthCode,
        @NotNull(message = "emailAuthCodeByServer 빠져있음")
        String emailAuthCodeByServer
) {
}
