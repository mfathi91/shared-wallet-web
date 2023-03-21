package com.example.sharedwallet.controller;

import com.example.sharedwallet.AppConfig;
import com.example.sharedwallet.model.Balance;
import com.example.sharedwallet.model.Payment;
import com.example.sharedwallet.repository.BalanceRepository;
import com.example.sharedwallet.repository.PaymentRepository;
import com.example.sharedwallet.repository.UserRepository;
import com.example.sharedwallet.repository.WalletRepository;
import com.example.sharedwallet.view.BalanceView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class Controllers {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    private final Logger logger = LoggerFactory.getLogger(Controllers.class);

    @GetMapping("/")
    public String home(final Model model) {

        return "home.html";
    }

    @GetMapping("/updateWallet")
    public String updateWalletGet(final Model model) {

        model.addAttribute("wallets", walletRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "update-wallet.html";
    }

    @PostMapping("/updateWallet")
    public String updateWalletPost(@RequestParam("wallet") String currency, @RequestParam("payer") String payerUsername,
            @RequestParam BigDecimal amount, @RequestParam String note) {

        final var payer = userRepository.findByUsernameOrThrow(payerUsername);
        final var payee = userRepository.findByUsernameNotEqualOrThrow(payerUsername);
        final var wallet = walletRepository.findByCurrencyOrThrow(currency);

        /* Calculate the new balance. */
        final var oldBalanceOptional = balanceRepository.findByWallet(wallet);
        if (oldBalanceOptional.isEmpty()) {
            balanceRepository.save(new Balance(wallet, payer, amount));
        } else {
            var oldBalance = oldBalanceOptional.orElseThrow();
            final BigDecimal oldAmount = oldBalance.getAmount();
            if (oldBalance.getUser().getUsername().equals(payer.getUsername())) {
                oldBalance.setAmount(oldAmount.add(amount));
                balanceRepository.save(oldBalance);
            } else {
                if (oldAmount.subtract(amount).compareTo(BigDecimal.ZERO) > 0) {
                    oldBalance.setAmount(oldAmount.subtract(amount));
                } else {
                    oldBalance.setAmount(amount.subtract(oldAmount));
                    oldBalance.setUser(payer);
                }
                balanceRepository.save(oldBalance);
            }
        }
        paymentRepository.save(new Payment(wallet, payer, amount, note, LocalDateTime.now()));
        return "redirect:/status";
    }

    @GetMapping("/status")
    public String status(final Model model) {

        final var balanceViewList = new ArrayList<>();
        for (final var balance : balanceRepository.findAll()) {
            balanceViewList.add(new BalanceView(balance.getWallet().getCurrency(), balance.getAmount(), balance.getUser().getUsername(), "Debtor"));
        }
        model.addAttribute("balances", balanceViewList);
//        model.addAttribute("payments", Database.getPayments());
        return "status.html";
    }

    @GetMapping("/payments")
    public String payments(final Model model) {

//        model.addAttribute("payments", Database.getPayments());
        return "payments.html";
    }
}
