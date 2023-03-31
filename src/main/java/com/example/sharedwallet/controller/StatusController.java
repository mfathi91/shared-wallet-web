package com.example.sharedwallet.controller;

import com.example.sharedwallet.model.Balance;
import com.example.sharedwallet.model.User;
import com.example.sharedwallet.repository.BalanceRepository;
import com.example.sharedwallet.repository.UserRepository;
import com.example.sharedwallet.view.BalanceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StatusController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @GetMapping("/status")
    public String status(final Model model) {

        final var users = userRepository.findAll();
        final var user1 = users.get(0);
        final var user2 = users.get(1);
        model.addAttribute("username1", user1.getUsername());
        model.addAttribute("username2", user2.getUsername());
        final var balances = balanceRepository.findAll(Sort.by(Sort.Direction.ASC, "wallet.currency"));
        model.addAttribute("balances", getBalanceViews(balances, user1, user2));
        return "status.html";
    }

    private List<BalanceView> getBalanceViews(final List<Balance> balances, final User user1, final User user2) {

        final List<BalanceView> balanceViewList = new ArrayList<>();
        for (final var balance : balances) {
            final var creditor = balance.getUser();
            final String userBalance1;
            final String userBalance2;
            if (creditor.equals(user1)) {
                userBalance1 = String.format("%s %s", balance.getAmount(), balance.getWallet().getSymbol());
                userBalance2 = String.format("0 %s", balance.getWallet().getSymbol());
            } else if (creditor.equals(user2)) {
                userBalance1 = String.format("0 %s", balance.getWallet().getSymbol());
                userBalance2 = String.format("%s %s", balance.getAmount(), balance.getWallet().getSymbol());
            } else {
                throw new IllegalStateException("Creditor user is not equal to the existing users");
            }
            balanceViewList.add(new BalanceView(balance.getWallet().getCurrency(), userBalance1, userBalance2));
        }
        return balanceViewList;
    }
}
