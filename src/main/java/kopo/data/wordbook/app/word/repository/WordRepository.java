package kopo.data.wordbook.app.word.repository;

import kopo.data.wordbook.app.word.repository.document.WordDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WordRepository extends MongoRepository<WordDocument, ObjectId> {
    List<WordDocument> findAllByWord(String word);
}
