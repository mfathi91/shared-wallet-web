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
            for (int i = 0; i< appConfig.wallets().size(); i++) {
                final var configuredWallet = appConfig.wallets().get(i);
                walletRepository.save(new Wallet((long) i+1, configuredWallet.currency(), configuredWallet.symbol()));
            }
        }

        if (userRepository.count() == 0) {
            for (int i = 0; i< appConfig.users().size(); i++) {
                final var configuredWallet = appConfig.users().get(i);
                userRepository.save(new User((long) i+1, configuredWallet.username(), configuredWallet.password()));
            }
        }
    }
}
