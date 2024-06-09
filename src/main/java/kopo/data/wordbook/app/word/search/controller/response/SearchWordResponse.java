package kopo.data.wordbook.app.word.search.controller.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
public record SearchWordResponse(
         String wordName,
        List<WordMeaning> wordMeaningList
) {


}
