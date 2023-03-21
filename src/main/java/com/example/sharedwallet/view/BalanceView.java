package com.example.sharedwallet.view;

import java.math.BigDecimal;


public record BalanceView(String currency, BigDecimal amount, String creditor, String debtor) {
}
