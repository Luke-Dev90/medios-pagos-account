package com.lchalela.medios.pagos.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@RequestBody AccountCreateDTO accountCreateDto){
		return null;
	}

}
