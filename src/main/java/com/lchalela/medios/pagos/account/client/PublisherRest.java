package com.lchalela.medios.pagos.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lchalela.medios.pagos.account.dto.NewTransactionDTO; 

@FeignClient(name = "ms-publiher", url = "localhost:8086")
public interface PublisherRest {
	@PostMapping("api/v1/new")
	public ResponseEntity<String> createMessage(@RequestBody NewTransactionDTO transaction);
}
