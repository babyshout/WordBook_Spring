package kopo.data.wordbook.app.word.myword.controller.response;

import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record MywordResponse(
        String mywordName,
        List<String> wordNameList
) {
    public static MywordResponse of(MywordEntity entity) {

        return MywordResponse.builder()
                .mywordName(entity.getMywordName())
                .wordNameList(List.copyOf(entity.getWordNameList()))
                .build();
    }
}
