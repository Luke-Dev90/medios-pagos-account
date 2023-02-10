package com.lchalela.medios.pagos.account.service;

import java.util.List;

import com.lchalela.medios.pagos.account.dto.AccountCompletDTO;
import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;
import com.lchalela.medios.pagos.account.dto.AccountDTOresponse;
import com.lchalela.medios.pagos.account.dto.AccountResponseDTO;
import com.lchalela.medios.pagos.account.dto.NewTransactionDTO;
import com.lchalela.medios.pagos.account.model.Account;

public interface AccountService {
	List<AccountCompletDTO> getAccountByUserID(Long id) throws Exception;
	
	AccountResponseDTO getAccountByCbuOrAlias(String cbu, String alias) throws Exception;
	Account getAccountEntityByCbuOrAlias(String cbu, String alias);
	public AccountDTOresponse transferByCbuOrAlias(NewTransactionDTO transaction) throws Exception;
	public void deleteAccount(Long id);
	List<AccountCompletDTO>  createAccount(AccountCreateDTO accountCreateDTO);
	public void impactAsynTransaction(String message);
}
