package kopo.data.wordbook.app.student.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginRequestBody(
        @NotNull
        String studentId,
        @NotNull
        String password
) {
}
