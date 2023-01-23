package com.lchalela.medios.pagos.account.mapper;

import org.mapstruct.Mapper;

import com.lchalela.medios.pagos.account.dto.AccountCompletDTO;
import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;
import com.lchalela.medios.pagos.account.dto.AccountResponseDTO;
import com.lchalela.medios.pagos.account.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	Account accountDtoToAccount(AccountCompletDTO account);
	Account accountCreateTOaccount(AccountCreateDTO accountCreateDTO);
	AccountResponseDTO accountToAccountResponseDTO(Account account);
	AccountCompletDTO accountToAccountCompleteDTO(Account account);
}
