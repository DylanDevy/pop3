package pop.service;

import database.manager.UserDetailsManager;
import database.repository.UserDetailsRepository;
import pop.service.popcommand.PassCommandExecutor;
import pop.service.popcommand.PopCommandExecutor;
import pop.service.popcommand.RegisterCommandExecutor;
import pop.service.popcommand.UserCommandExecutor;
import security.service.Encryptor;

public class BaseSessionBuilder {
    public static PopSessionThread.Builder getPopSessionThreadBuilder() {
        PopSessionThread.Builder popSessionThreadBuilder = new PopSessionThread.Builder();

        popSessionThreadBuilder
                .setPopCommandExecutor(new PopCommandExecutor.Builder()
                        .addPopCommand(new RegisterCommandExecutor.Builder()
                                .setEncryptor(new Encryptor.Builder()
                                        .setIterations(10000)
                                        .setPbeKeyLength(256)
                                        .setSaltLength(32)
                                        .setDelimiter(":")
                                        .build()
                                )
                                .setUserDetailsRepository(new UserDetailsRepository.Builder()
                                        .build())
                                .setUserDetailsManager(new UserDetailsManager.Builder()
                                        .build()
                                )
                                .build(),
                                "REGISTER"
                        )
                        .addPopCommand(new UserCommandExecutor.Builder()
                                .setUserDetailsRepository(new UserDetailsRepository.Builder()
                                        .build()
                                )
                                .build(), "USER"
                        )
                        .addPopCommand(new PassCommandExecutor.Builder()
                                .setEncryptor(new Encryptor.Builder()
                                        .setIterations(10000)
                                        .setPbeKeyLength(256)
                                        .setSaltLength(32)
                                        .setDelimiter(":")
                                        .build()
                                )
                                .build(), "PASS"
                        )
                        .build()
                )
        ;

        return popSessionThreadBuilder;
    }
}
