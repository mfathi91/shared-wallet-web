package com.example.sharedwallet;

import com.example.sharedwallet.model.Payment;
import com.example.sharedwallet.model.User;
import com.example.sharedwallet.model.Wallet;
import com.example.sharedwallet.repository.PaymentRepository;
import com.example.sharedwallet.repository.UserRepository;
import com.example.sharedwallet.repository.WalletRepository;
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
    public String updateWalletPost(@RequestParam("wallet") String currency, @RequestParam("payer") String payer,
            @RequestParam BigDecimal amount, @RequestParam String note) {

        final User foundPayer = userRepository.findByUsername(payer)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No user with name [%s] found!", payer)));
        final Wallet foundWallet = walletRepository.findByCurrency(currency)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No wallet with currency [%s] found", currency)));
        if (foundPayer != null && foundWallet != null) {
            logger.info(String.format("Payer %s found", foundPayer));
            logger.info(String.format("Wallet %s found", foundWallet));
            paymentRepository.save(new Payment(foundWallet, foundPayer, amount, note, LocalDateTime.now()));
        }
        return "success.html";
    }

    @GetMapping("/payments")
    public String payments(final Model model) {

//        model.addAttribute("payments", Database.getPayments());
        return "payments.html";
    }
}
