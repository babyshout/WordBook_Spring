package kopo.data.wordbook.app.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateStudentRequest(
        @NotBlank(message = "ID 는 필수입력 입니다!")
        String studentId,
        @NotBlank(message = "name 은 필수입력 사항입니다!")
        String name,
        @NotBlank(message = "email 은 필수입력 사항입니다!")
        String email,
        String emailVerificationCode,
        @NotBlank(message = "password 는 필수입력 사항입니다!")
        String password,
        String passwordConfirm

) {
}
