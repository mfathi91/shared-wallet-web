package com.example.sharedwallet;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app-config")
public record AppConfig(List<Wallet> wallets, List<User> users) {
}

record Wallet(String currency, String symbol) {
}

record User(String username, String password) {
}