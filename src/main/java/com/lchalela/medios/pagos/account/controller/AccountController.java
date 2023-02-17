package com.lchalela.medios.pagos.account.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lchalela.medios.pagos.account.dto.AccountCompletDTO;
import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;
import com.lchalela.medios.pagos.account.dto.AccountDTOresponse;
import com.lchalela.medios.pagos.account.dto.NewTransactionDTO;
import com.lchalela.medios.pagos.account.service.AccountService;

@RestController
@RequestMapping("/v1")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@RequestBody AccountCreateDTO accountCreateDto){
		logger.info("Init request create account");
		List<AccountCompletDTO> account = this.accountService.createAccount(accountCreateDto);
		logger.info("return account");
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}
	
	@PostMapping("/check")
	public ResponseEntity<?> checkAccounts(@Valid @RequestBody NewTransactionDTO transaction) throws Exception{
		logger.info("Request check account");
		AccountDTOresponse account = this.accountService.transferByCbuOrAlias(transaction);
		return new ResponseEntity<>(account,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) throws Exception{
		logger.info("Request get account by user id");
		return new ResponseEntity<>(this.accountService.getAccountByUserID(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{nroAccount}")
	public ResponseEntity<?> deleteAccount(@PathVariable Long nroAccount){
		logger.info("Request delete account by nroAccount");
		this.accountService.deleteAccount(nroAccount);
		return new ResponseEntity<>("Account deleted", HttpStatus.OK);
	}
	
}
