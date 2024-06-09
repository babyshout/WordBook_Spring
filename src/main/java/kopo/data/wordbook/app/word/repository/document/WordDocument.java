package kopo.data.wordbook.app.word.repository.document;

import jakarta.persistence.Id;
import kopo.data.wordbook.app.word.rest.client.impl.StdictKoreanSearchApiResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Word")
@Getter
@EqualsAndHashCode
@Builder
@Slf4j
@ToString
public class WordDocument {
    @Id
    private ObjectId id;
    /**
     * 단어 명
     */
    @Field
    @Indexed(unique = true)
    private String wordName;

    @Setter
    private List<WordDetail> wordDetailList;

    @Data
    @Builder
    public static class WordDetail {
        /**
         * 보조 번호
         */
        private Integer supNo;
        /**
         * 정의
         */
        private String definition;
        /**
         * 사전 링크
         */
        private String link;
        /**
         * 단어 타입
         */
        private String type;
        /**
         * 단어 고유 코드
         */
        private Integer targetCode;
        /**
         * 품사
         */
        private String pos;


        public static List<WordDetail> of(StdictKoreanSearchApiResponse apiResponse) {

            String wordName = apiResponse.getChannel().getItem().get(0).getWord();
            List<WordDetail> wordDetailListFromApiResponse = new ArrayList<>();


            apiResponse.getChannel().getItem().forEach(item -> {
                WordDetail wordDetail = WordDetail.builder()
                        .definition(item.getSense().getDefinition())
                        .pos(item.getPos())
                        .link(item.getSense().getLink())
                        .type(item.getSense().getType())
                        .supNo(item.getSupNo())
                        .targetCode(item.getTargetCode()).build();
                String wordNameInItem =
                        apiResponse.getChannel().getItem().get(0).getWord();

                log.trace("wordDetail -> {}", wordDetail);

                if (!wordName.equals(wordNameInItem)) {
                    log.error("wordName and wordNameInItem is not EQUAL!!!!!!!!!!");
                    return;
                }

                wordDetailListFromApiResponse.add(wordDetail);
            });
            log.trace("wordDetailListFromApiResponse -> {}", wordDetailListFromApiResponse);


            return wordDetailListFromApiResponse;
        }

    }

    public static WordDocument of(StdictKoreanSearchApiResponse apiResponse) {
        String wordName = apiResponse.getChannel().getItem().get(0).getWord();

        List<WordDetail> wordDetails = WordDetail.of(apiResponse);
        WordDocument result = WordDocument.builder()
                .wordDetailList(wordDetails)
                .wordName(wordName)
                .build();

        log.trace("result -> {}", result);

        return result;
    }


}
