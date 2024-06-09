package kopo.data.wordbook.app.student.mypage.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PatchStudentPasswordRequest(
        @NotNull(message = "nowPassword is NotNull")
        String nowPassword,
        @NotNull(message = "newPassword is NotNull")
        String newPassword,
        @NotNull(message = "newPasswordConfirm is NotNull")
        String newPasswordConfirm
) {
}
