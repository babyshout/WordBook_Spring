package kopo.data.wordbook.app.word.repository;

import kopo.data.wordbook.app.word.repository.document.WordDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends MongoRepository<WordDocument, ObjectId> {
    List<WordDocument> findAllByWordName(String wordName);

    Optional<WordDocument> findByWordName(String wordName);
}
