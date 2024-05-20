package kopo.data.wordbook;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

public class EnumTest {

    public enum HandleURL {
        BASE("/api/student/v1/login"),
        GET_LOGIN("/getLogin"),
        ;

        public final String path;


        HandleURL(String path) {
            this.path = path;
        }
    }

    @Test
    public void HandleURL_return_correctPath() {
        String url = HandleURL.BASE.path;
        assertThat(url).isEqualTo("/api/student/v1/login");
    }
}
