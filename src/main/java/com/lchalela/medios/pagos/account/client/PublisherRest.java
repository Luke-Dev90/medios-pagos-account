package com.lchalela.medios.pagos.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lchalela.medios.pagos.account.dto.NewTransactionDTO; 

@FeignClient(name = "ms-publisher")
public interface PublisherRest {
	@PostMapping("/v1/new")
	public ResponseEntity<String> createMessage(@RequestBody NewTransactionDTO transaction);
}
