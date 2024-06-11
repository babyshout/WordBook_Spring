package kopo.data.wordbook.app.student.social.repository.entity;

import kopo.data.wordbook.app.student.social.repository.entity.constant.SocialLoginProvider;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@Builder
public class SocialLoginEntityId implements Serializable {
    private String id_bySocialLoginProvider;
    private SocialLoginProvider provider;

}
