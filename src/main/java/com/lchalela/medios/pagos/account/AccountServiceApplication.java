package com.lchalela.medios.pagos.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.lchalela.medios.pagos.account.client.TransactionRest;

@SpringBootApplication()
@EnableFeignClients(clients = {TransactionRest.class})
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
