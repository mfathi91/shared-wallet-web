package com.example.sharedwallet;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myconfig")
public record MyConfig(String name, int age, String city) {
}
