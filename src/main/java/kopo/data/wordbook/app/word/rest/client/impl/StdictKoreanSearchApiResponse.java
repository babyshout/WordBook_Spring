package kopo.data.wordbook.app.word.rest.client.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

//@Getter
//@Setter
@Data
public class StdictKoreanSearchApiResponse {

    private Channel channel;

    // Getters and setters

    //    @Getter
//    @Setter
    @Data
    public static class Channel {

        private int total;
        private int num;
        private String title;
        private int start;
        private String description;
        private List<Item> item;
        private String link;
        private String lastbuilddate;

        // Getters and setters

        //        @Getter
//        @Setter
        @Data
        public static class Item {

            @JsonProperty("sup_no")
            private Integer supNo;
            private String word;
            @JsonProperty("target_code")
            private Integer targetCode;
            private Sense sense;
            private String pos;

            // Getters and setters

            //            @Getter
//            @Setter
            @Data
            public static class Sense {
                private String definition;
                private String link;
                private String type;

                // Getters and setters
            }
        }
    }
}