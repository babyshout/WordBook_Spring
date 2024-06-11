package kopo.data.wordbook.app.word.search.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SearchWordRequst(
        @NotNull(message = "wordName 이 null 임")
        String wordName
) {
}
