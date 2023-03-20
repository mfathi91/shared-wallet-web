package com.example.sharedwallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private User user;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String note;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    public Payment() {
    }

    public Payment(Wallet wallet, User user, BigDecimal amount, String note, LocalDateTime dateTime) {
        this.wallet = wallet;
        this.user = user;
        this.amount = amount;
        this.note = note;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", wallet=" + wallet +
                ", user=" + user +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
