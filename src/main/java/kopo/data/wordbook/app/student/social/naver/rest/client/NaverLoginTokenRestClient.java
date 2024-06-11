package kopo.data.wordbook.app.student.social.naver.rest.client;

import kopo.data.wordbook.app.student.social.naver.rest.client.response.AuthorizationCodeResponse;
import kopo.data.wordbook.app.student.social.naver.rest.client.response.NidMeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverLoginTokenRestClient {
    @Value("${naver.api.login.client_id}")
    private String client_id;
    @Value("${naver.api.login.client_secret}")
    private String client_secret;


    public AuthorizationCodeResponse get_authorization_code(String code, String state) {
        URI authorizationCodeUri = this.get_authorization_code_uri(code, state);

        RestClient restClient = RestClient.builder()
                .baseUrl(authorizationCodeUri.toString())
                .build();

        ResponseEntity<AuthorizationCodeResponse> entity =
                restClient.post().retrieve().toEntity(AuthorizationCodeResponse.class);


        return entity.getBody();
    }


    @Value("${naver.api.login.token.request_url}")
    private String token_request_url;
    @Value("${naver.api.login.token.grant_type.authorization_code.grant_type}")
    private String authorization_grant_type;

    public URI get_authorization_code_uri(String code, String state) {

        UriComponents build = UriComponentsBuilder.fromHttpUrl(token_request_url)
                .queryParam("grant_type", authorization_grant_type)
                .queryParam("client_id", client_id)
                .queryParam("client_secret", client_secret)
                .queryParam("code", code)
                .queryParam("state", state)
                .build();

        log.trace("{}", build.toString());

        log.trace("build.toUri() -> {}", build.toUri());
        return build.toUri();
    }


    @Value("${naver.api.login.nid_me.request_url}")
    private String nidme_request_url;

    public NidMeResponse getNidmeResponse(String accessToken) {
        RestClient restClient = RestClient.builder()
                .baseUrl(nidme_request_url)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();

        log.trace("nidme_request_url -> {}", nidme_request_url);
        log.trace("accessToken -> {}", "Bearer " + accessToken);
        ResponseEntity<NidMeResponse> entity = restClient.post().retrieve().toEntity(NidMeResponse.class);

        log.trace("entity.getBody().toString() -> {}", entity.getBody().toString());


        return entity.getBody();
    }
}
