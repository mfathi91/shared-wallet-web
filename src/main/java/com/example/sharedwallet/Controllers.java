package com.example.sharedwallet;

import com.example.sharedwallet.database.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controllers {

    @GetMapping("/")
    public String home(final Model model) {

        return "home.html";
    }

    @GetMapping("/updateWallet")
    public String updateWallet(final Model model) {

        return "update-wallet.html";
    }

    @GetMapping("/payments")
    public String payments(final Model model) {

        model.addAttribute("payments", Database.getPayments());
        return "payments.html";
    }
}
