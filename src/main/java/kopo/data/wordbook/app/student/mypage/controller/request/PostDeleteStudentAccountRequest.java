package kopo.data.wordbook.app.student.mypage.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PostDeleteStudentAccountRequest(
        @NotNull(message = "nowPassword 가 비어있음")
        String nowPassword,
        @NotNull(message = "deleteConfirmMessage 가 비어있음")
        String deleteConfirmMessage
) {
}
