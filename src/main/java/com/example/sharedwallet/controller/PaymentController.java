package com.example.sharedwallet.controller;

import com.example.sharedwallet.repository.PaymentRepository;
import com.example.sharedwallet.view.PaymentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

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
