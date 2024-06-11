package kopo.data.wordbook.app.student.social.repository.entity;

import jakarta.persistence.Embeddable;
import kopo.data.wordbook.app.student.social.repository.entity.constant.SocialLoginProvider;
import lombok.*;

import java.io.Serializable;
@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginEntityId implements Serializable {

    private String id_bySocialLoginProvider;
    private SocialLoginProvider provider;

}
