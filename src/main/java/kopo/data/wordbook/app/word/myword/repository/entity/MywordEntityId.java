package kopo.data.wordbook.app.word.myword.repository.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MywordEntityId implements Serializable {
    private String student;
    private String mywordName;
}
