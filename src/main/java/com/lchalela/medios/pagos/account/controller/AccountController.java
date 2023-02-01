package com.lchalela.medios.pagos.account.controller;

import java.util.List;

import javax.validation.Valid;

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
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@RequestBody AccountCreateDTO accountCreateDto){
		List<AccountCompletDTO> account = this.accountService.createAccount(accountCreateDto);
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}
	
	@PostMapping("/check")
	public ResponseEntity<?> checkAccounts(@Valid @RequestBody NewTransactionDTO transaction) throws Exception{
		AccountDTOresponse account = this.accountService.transferByCbuOrAlias(transaction);
		return new ResponseEntity<>(account,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) throws Exception{
		return new ResponseEntity<>(this.accountService.getAccountByUserID(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{nroAccount}")
	public ResponseEntity<?> deleteAccount(@PathVariable String nroAccount){
		this.accountService.deleteAccount(nroAccount);
		return null;
	}
	
}
