package kopo.data.wordbook.app.word.comment.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntityId implements Serializable {
    private String wordName;
    private Long wordCommentSeq;
}
