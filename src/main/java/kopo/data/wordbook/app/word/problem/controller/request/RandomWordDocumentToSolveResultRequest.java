package kopo.data.wordbook.app.word.problem.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RandomWordDocumentToSolveResultRequest(
        @NotNull(message = "기존 단어 이름이 비어있습니다!!!")
        String wordName,
        @NotNull(message = "단어장 이름이 비어있습니다!!")
        String mywordName,
        @NotNull(message = "단어 대답이 비어있습니다!!")
        String wordNameAnswer
) {
}
