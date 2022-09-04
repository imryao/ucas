package org.mryao.ucas;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.security.Security;

/**
 * @author mryao
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class UcasApplication {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        SpringApplication.run(UcasApplication.class, args);
    }
}
