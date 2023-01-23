package com.lchalela.medios.pagos.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lchalela.medios.pagos.account.dto.AccountCompletDTO;
import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;
import com.lchalela.medios.pagos.account.dto.AccountResponseDTO;
import com.lchalela.medios.pagos.account.mapper.AccountMapper;
import com.lchalela.medios.pagos.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;
	private AccountMapper accountMapper;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
		this.accountRepository = accountRepository;
		this.accountMapper = accountMapper;
	}

	@Override
	public AccountCompletDTO getAccountByUserID(Long id) throws Exception {
		return this.accountMapper
				.accountToAccountCompleteDTO(Optional.of(this.accountRepository.findAccountByUserId(id))
						.orElseThrow(() -> new Exception("Account not found")));
	}

	@Override
	public AccountResponseDTO getAccountByCbuOrAlias(String cbu, String alias) throws Exception {
		return this.accountMapper
				.accountToAccountResponseDTO(Optional.of(this.accountRepository.findAccountByCbuOrAlias(cbu, alias))
						.orElseThrow(() -> new Exception("Account not found")));
	}

	@Override
	public AccountCompletDTO createAccount(AccountCreateDTO accountCreateDTO) {
		accountCreateDTO.setBalance(new BigDecimal(0));
		return this.accountMapper.accountToAccountCompleteDTO(
				this.accountRepository.save(this.accountMapper.accountCreateTOaccount(accountCreateDTO)));
	}

	@Override
	public void transferByCbuOrAlias(String cbuOrigin, String cbuDestination, String aliasDestination,
			BigDecimal amount) {
		// TODO Auto-generated method stub

	}

}
