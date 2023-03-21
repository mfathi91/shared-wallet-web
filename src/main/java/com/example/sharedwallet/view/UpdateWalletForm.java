package com.example.sharedwallet.view;

import java.math.BigDecimal;

public class UpdateWalletForm {

    private String currency;
    private String payer;
    private BigDecimal amount;
    private String note;

    public UpdateWalletForm() {
    }

    public UpdateWalletForm(String currency, String payer, BigDecimal amount, String note) {
        this.currency = currency;
        this.payer = payer;
        this.amount = amount;
        this.note = note;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateWalletForm that = (UpdateWalletForm) o;

        if (!currency.equals(that.currency)) return false;
        if (!payer.equals(that.payer)) return false;
        if (!amount.equals(that.amount)) return false;
        return note.equals(that.note);
    }

    @Override
    public int hashCode() {
        int result = currency.hashCode();
        result = 31 * result + payer.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + note.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UpdateWalletForm{" +
                "currency='" + currency + '\'' +
                ", payer='" + payer + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    }
}
