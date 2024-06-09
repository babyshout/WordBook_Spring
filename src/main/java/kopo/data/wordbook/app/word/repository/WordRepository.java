package kopo.data.wordbook.app.word.repository;

import kopo.data.wordbook.app.word.repository.document.WordDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends MongoRepository<WordDocument, ObjectId> {
    List<WordDocument> findAllByWordName(String wordName);

    WordDocument findByWordName(String wordName);
}
