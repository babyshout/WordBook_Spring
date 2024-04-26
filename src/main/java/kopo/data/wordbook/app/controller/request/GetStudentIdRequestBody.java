package kopo.data.wordbook.app.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record GetStudentIdRequestBody(
        @NotNull
        String name,
        @NonNull
        String email
) {
}
