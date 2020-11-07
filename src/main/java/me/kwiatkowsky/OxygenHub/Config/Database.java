package me.kwiatkowsky.OxygenHub.Config;


import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class Database extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${spring.data.mongodb.database}")
    private String database;


    @Override
    public MongoClient mongoClient() {
        return new MongoClient(host + ":" + port);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
