package com.example.sharedwallet.controller;

import com.example.sharedwallet.model.Balance;
import com.example.sharedwallet.model.Payment;
import com.example.sharedwallet.model.User;
import com.example.sharedwallet.model.Wallet;
import com.example.sharedwallet.repository.BalanceRepository;
import com.example.sharedwallet.repository.PaymentRepository;
import com.example.sharedwallet.repository.UserRepository;
import com.example.sharedwallet.repository.WalletRepository;
import com.example.sharedwallet.view.UpdateWalletForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/updateWallet")
    public String updateWalletGet(final Model model) {

        model.addAttribute("wallets", walletRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("updateWalletForm", new UpdateWalletForm());
        return "update-wallet.html";
    }

    @PostMapping("/updateWallet")
    public String updateWalletPost(@ModelAttribute("updateWalletForm") UpdateWalletForm form, Model model) {

        final var payer = userRepository.findByUsernameOrThrow(form.getPayer());
        final var wallet = walletRepository.findByCurrencyOrThrow(form.getCurrency());
        balanceRepository.save(calcNewBalance(form, payer, wallet));
        paymentRepository.save(new Payment(wallet, payer, form.getAmount(), form.getNote(), LocalDateTime.now()));
        return "redirect:/status";
    }

    private Balance calcNewBalance(final UpdateWalletForm form, final User payer, final Wallet wallet) {

        Balance newBalance;
        final var amount = form.getAmount();
        final var currBalanceOptional = balanceRepository.findByWallet(wallet);
        if (currBalanceOptional.isEmpty()) {
            newBalance = new Balance(wallet, payer, amount);
        } else {
            final var currBalance = currBalanceOptional.orElseThrow();
            final var currBalanceAmount = currBalance.getAmount();
            final var currBalanceCreditor = currBalance.getUser();
            if (currBalanceCreditor.getUsername().equals(form.getPayer())) {
                currBalance.setAmount(amount.add(currBalanceAmount));
            } else {
                if (currBalanceAmount.subtract(amount).compareTo(BigDecimal.ZERO) > 0) {
                    currBalance.setAmount(currBalanceAmount.subtract(amount));
                } else {
                    currBalance.setAmount(amount.subtract(currBalanceAmount));
                    currBalance.setUser(payer);
                }
            }
            newBalance = currBalance;
        }
        return newBalance;
    }
}
