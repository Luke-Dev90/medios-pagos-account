package com.lchalela.medios.pagos.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.lchalela.medios.pagos.account.dto.TransactionDTO;

@FeignClient(name = "transaction-service" , url = "localhost:8084")
public interface TransactionRest {

	@PostMapping("/transaction/new")
	public void registerTransaction(TransactionDTO transactionDTO);
}
