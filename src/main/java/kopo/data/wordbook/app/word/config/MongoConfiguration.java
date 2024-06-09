//package kopo.data.wordbook.app.word.config;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoDatabase;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//@Slf4j
//public class MongoConfiguration {
//    @Bean
//    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
//        MongoDatabase myDB = mongoClient.getDatabase("MyDB");
//        log.error("myDB.getName() -> {}",myDB.getName());
//        log.error("myDB.toString() -> {}",myDB.toString());
//        return new MongoTemplate(mongoClient, "MyDB");
//    }
//}
