package kopo.data.wordbook.app.student.social.repository;

import kopo.data.wordbook.app.student.social.repository.entity.SocialLoginEntity;
import kopo.data.wordbook.app.student.social.repository.entity.SocialLoginEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginRepository extends JpaRepository<SocialLoginEntity, SocialLoginEntityId> {

}
