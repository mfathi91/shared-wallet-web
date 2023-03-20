package com.example.sharedwallet;

import com.example.sharedwallet.model.User;
import com.example.sharedwallet.model.Wallet;
import com.example.sharedwallet.repository.UserRepository;
import com.example.sharedwallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        if (walletRepository.count() == 0) {
            for (final var wallet : appConfig.wallets()) {
                walletRepository.save(new Wallet(wallet.currency(), wallet.symbol()));
            }
        }

        if (userRepository.count() == 0) {
            for (final var user : appConfig.users()) {
                userRepository.save(new User(user.username(), user.password()));
            }
        }
    }
}
