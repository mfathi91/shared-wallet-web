package com.example.sharedwallet.database;

public record Transaction(String wallet, String payer, int amount, String note) {
}
