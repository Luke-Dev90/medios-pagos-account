package com.lchalela.medios.pagos.account.service;

import java.math.BigDecimal;

import com.lchalela.medios.pagos.account.dto.AccountCompletDTO;
import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;
import com.lchalela.medios.pagos.account.dto.AccountResponseDTO;

public interface AccountService {
	AccountCompletDTO getAccountByUserID(Long id) throws Exception;
	AccountResponseDTO getAccountByCbuOrAlias(String cbu, String alias) throws Exception;
	void transferByCbuOrAlias(String cbuOrigin, String cbuDestination, String aliasDestination, BigDecimal amount);
	AccountCompletDTO createAccount(AccountCreateDTO accountCreateDTO);
}
