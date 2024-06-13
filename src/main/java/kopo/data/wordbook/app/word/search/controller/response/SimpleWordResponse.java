package kopo.data.wordbook.app.word.search.controller.response;

import kopo.data.wordbook.app.word.rest.client.impl.StdictKoreanSearchApiResponse;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record SimpleWordResponse(
        String wordName,
        String definition
) {
    public static List<SimpleWordResponse> listOf(List<StdictKoreanSearchApiResponse.Channel.Item> itemList) {
        List<SimpleWordResponse> responseList = new ArrayList<>();
        itemList.forEach(item -> {
            responseList.add(SimpleWordResponse.builder()
                    .wordName(item.getWord())
                    .definition(item.getSense().getDefinition())
                    .build());
        });

        return responseList;
    }
}
