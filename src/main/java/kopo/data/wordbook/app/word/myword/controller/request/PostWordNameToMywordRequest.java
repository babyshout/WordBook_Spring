package kopo.data.wordbook.app.word.myword.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PostWordNameToMywordRequest(
        @NotNull(message = "추가할 단어 이름이 비어 있음")
        String wordName,
        @NotNull(message = "추가할 단어장 이름이 비어 있음")
        String mywordName
) {
}
