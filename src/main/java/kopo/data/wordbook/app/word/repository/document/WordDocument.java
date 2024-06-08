package kopo.data.wordbook.app.word.repository.document;

import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Word")
@Getter
@EqualsAndHashCode
public class WordDocument {
    @Id
    private ObjectId id;
    /**
     * 단어 명
     */
    private String word;
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
    private String targetCode;
    /**
     * 품사
     */
    private String pos;
}
