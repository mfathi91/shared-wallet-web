package com.example.sharedwallet.controller;

import com.example.sharedwallet.AppConfig;
import com.example.sharedwallet.model.Balance;
import com.example.sharedwallet.model.Payment;
import com.example.sharedwallet.model.User;
import com.example.sharedwallet.model.Wallet;
import com.example.sharedwallet.repository.BalanceRepository;
import com.example.sharedwallet.repository.PaymentRepository;
import com.example.sharedwallet.repository.UserRepository;
import com.example.sharedwallet.repository.WalletRepository;
import com.example.sharedwallet.view.BalanceView;
import com.example.sharedwallet.view.PaymentView;
import com.example.sharedwallet.view.UpdateWalletForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = {"/", "/home"})
    public String home(final Model model) {

        return "home.html";
    }

    @GetMapping("/updateWallet")
    public String updateWalletGet(final Model model) {

        model.addAttribute("wallets", walletRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("updateWalletForm", new UpdateWalletForm());
        return "update-wallet.html";
    }

    @PostMapping("/updateWallet")
    public String updateWalletPost( @ModelAttribute("updateWalletForm") UpdateWalletForm form, Model model) {

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
            } else if (creditor.equals(user2)){
                userBalance1 = String.format("0 %s", balance.getWallet().getSymbol());
                userBalance2 = String.format("%s %s", balance.getAmount(), balance.getWallet().getSymbol());
            } else {
                throw new IllegalStateException("Creditor user is not equal to the existing users");
            }
            balanceViewList.add(new BalanceView(balance.getWallet().getCurrency(), userBalance1, userBalance2));
        }
        return balanceViewList;
    }

    @GetMapping("/payments")
    public String payments(final Model model) {

        final List<PaymentView> paymentViewList = new ArrayList<>();
        for (final var payment : paymentRepository.findAll()) {
            paymentViewList.add(new PaymentView(payment.getUser().getUsername(), payment.getAmount().toString(),
                    payment.getWallet().getCurrency(), payment.getNote(), payment.getDateTime().toString()));
        }
        model.addAttribute("payments", paymentViewList);
        return "payments.html";
    }
}
