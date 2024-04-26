package kopo.data.wordbook.app.controller.request;

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
