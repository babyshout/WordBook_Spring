package kopo.data.wordbook.app.word.myword.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PostAddNewMywordRequest(
        @NotNull(message = "추가할 newMywordName 은 비어있을수 없습니다")
        String newMywordName
) {
}
