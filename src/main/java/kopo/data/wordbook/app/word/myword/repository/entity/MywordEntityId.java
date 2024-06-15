package kopo.data.wordbook.app.word.myword.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MywordEntityId implements Serializable {
    private String student;
    private String mywordName;
}
