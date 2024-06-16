package kopo.data.wordbook.app.word.problem.reopsitory.entity;

import kopo.data.wordbook.app.word.myword.repository.entity.MywordEntityId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemOfWordEntityId implements Serializable {
    private MywordEntityId mywordEntity;
    private Long problemOfWordSeq;
}
