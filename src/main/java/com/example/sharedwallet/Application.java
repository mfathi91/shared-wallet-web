package com.example.sharedwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

    public static void main(final String[] args) {

        final String dbPath = System.getenv().get("DB_PATH");
        if (dbPath == null || !Files.isRegularFile(Paths.get(dbPath))) {
            throw new MissingEnvironmentVariableException("DB_PATH is either not defined, or not pointing to a valid file");
        }
        SpringApplication.run(Application.class, args);
    }

}
