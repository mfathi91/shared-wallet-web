package com.example.sharedwallet;

import com.example.sharedwallet.database.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controllers {

    @Autowired
    private MyConfig myConfig;

    @GetMapping("/")
    public String home(final Model model) {

        System.out.println(myConfig);
        return "home.html";
    }

    @GetMapping("/updateWallet")
    public String updateWalletGet(final Model model) {

        model.addAttribute("wallets", Database.getWallets());
        model.addAttribute("users", Database.getUsers());
        return "update-wallet.html";
    }

    @PostMapping("/updateWallet")
    public String updateWalletPost(@RequestParam("wallet") String wallet, @RequestParam("payer") String payer,
            @RequestParam int amount, @RequestParam String note) {

        System.out.println(wallet);
        System.out.println(payer);
        System.out.println(amount);
        System.out.println(note);
        return "success.html";
    }

    @GetMapping("/payments")
    public String payments(final Model model) {

        model.addAttribute("payments", Database.getPayments());
        return "payments.html";
    }
}
