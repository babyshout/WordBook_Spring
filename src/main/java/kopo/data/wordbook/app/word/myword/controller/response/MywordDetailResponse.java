package kopo.data.wordbook.app.word.myword.controller.response;

import kopo.data.wordbook.app.word.repository.document.WordDocument;
import lombok.Builder;

import java.util.List;

@Builder
public record MywordDetailResponse(
        String mywordName,
        List<WordDocument> wordDocumentList
) {
    public static MywordDetailResponse of(String mywordName, List<WordDocument> wordDocumentList) {

        return MywordDetailResponse.builder()
                .mywordName(mywordName)
                .wordDocumentList(List.copyOf(wordDocumentList))
                .build();
    }
}
