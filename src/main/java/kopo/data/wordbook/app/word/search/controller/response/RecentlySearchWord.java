package kopo.data.wordbook.app.word.search.controller.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RecentlySearchWord(
        String wordName
) {
}
