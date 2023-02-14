package com.lchalela.medios.pagos.account.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lchalela.medios.pagos.account.dto.CbuDTO;
import com.lchalela.medios.pagos.account.dto.TransactionDTO;

@FeignClient(name = "transaction-service")
public interface TransactionRest {

	@PostMapping("/v1/new")
	public void registerTransaction(TransactionDTO transactionDTO);
	
	@PostMapping("/v1/all")
	public List<TransactionDTO> getAllTransaction(@RequestBody CbuDTO accountOrigin);
}
