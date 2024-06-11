package kopo.data.wordbook.app.student.social.repository;

import kopo.data.wordbook.app.student.social.repository.entity.SocialLoginEntity;
import kopo.data.wordbook.app.student.social.repository.entity.SocialLoginEntityId;
import kopo.data.wordbook.app.student.social.repository.entity.constant.SocialLoginProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginRepository extends JpaRepository<SocialLoginEntity, SocialLoginEntityId> {

    SocialLoginEntity findByPrimaryKey_Provider(SocialLoginProvider primaryKey_provider);

}
