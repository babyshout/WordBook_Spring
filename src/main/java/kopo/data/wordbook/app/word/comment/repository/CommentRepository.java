package kopo.data.wordbook.app.word.comment.repository;

import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntity;
import kopo.data.wordbook.app.word.comment.repository.entity.CommentEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, CommentEntityId> {

    List<CommentEntity> findAllByWordName(String wordName);

}
