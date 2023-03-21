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

@Entity
@Table(name = "balances")
public class Balance {

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

    public Balance() {
    }

    public Balance(Wallet wallet, User user, BigDecimal amount) {
        this.wallet = wallet;
        this.user = user;
        this.amount = amount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (!wallet.equals(balance.wallet)) return false;
        if (!user.equals(balance.user)) return false;
        return amount.equals(balance.amount);
    }

    @Override
    public int hashCode() {
        int result = wallet.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", wallet=" + wallet +
                ", user=" + user +
                ", amount=" + amount +
                '}';
    }
}
