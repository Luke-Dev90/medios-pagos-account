package com.lchalela.medios.pagos.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.lchalela.medios.pagos.account.dto.AccountCompletDTO;
import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;
import com.lchalela.medios.pagos.account.dto.AccountDTOresponse;
import com.lchalela.medios.pagos.account.dto.AccountResponseDTO;
import com.lchalela.medios.pagos.account.dto.NewTransactionDTO;
import com.lchalela.medios.pagos.account.mapper.AccountMapper;
import com.lchalela.medios.pagos.account.model.Account;
import com.lchalela.medios.pagos.account.repository.AccountRepository;
import static com.lchalela.medios.pagos.account.utils.NumberGenerator.generateNumbers;;

@Service
public class AccountServiceImpl implements AccountService {

	private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	private AccountRepository accountRepository;
	private AccountMapper accountMapper;
	
	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
		this.accountRepository = accountRepository;
		this.accountMapper = accountMapper;
	}

	@Override
	public List<AccountCompletDTO> getAccountByUserID(Long id) throws Exception {	
		List<Account> accountDTO = Stream.of( 
				this.accountRepository.findAccountByUserId(id) 
				).collect(Collectors.toList());
		return this.accountMapper.accountToAccountCompleteDTO( accountDTO );
	}

	@Override
	public AccountResponseDTO getAccountByCbuOrAlias(String cbu, String alias) throws Exception {
		Account account = Optional.of( this.accountRepository.findAccountByCbuOrAlias(cbu, alias))
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
		
		return this.accountMapper.accountToAccountResponseDTO(account);
	}

	@Override
	public List<AccountCompletDTO> createAccount(AccountCreateDTO accountCreateDTO) {
		logger.info("Inicio de la creacion de cuenta");
		Account account = new Account();
		
		account.setBalance( new BigDecimal(0));
		account.setTypeAccount( accountCreateDTO.getTypeAccount());
		account.setAccountNumber( generateNumbers(10) );
		account.setCbu(generateNumbers(22));
		
		account.setAlias( accountCreateDTO.getLastName()
				.concat(".alias.")
				.concat(generateNumbers(4))
				.concat(".").concat(accountCreateDTO.getName())
				);
		account.setUserId( accountCreateDTO.getUserId());

		return this.accountMapper
				.accountToAccountCompleteDTO( List.of( this.accountRepository.save( account)) );
	}

	@Override
	@Transactional
	public AccountDTOresponse transferByCbuOrAlias(NewTransactionDTO transaction) throws Exception {
		// find the accounts.
		
		Account accountOrigin = this.getAccountEntityByCbuOrAlias(transaction.getAccountOrigin(), "");
		
		Account accountDestination = this.getAccountEntityByCbuOrAlias(
										transaction.getCbuDestination(),
										transaction.getAliasDestination());
		//TODO: Validar que este dentro del dia
		
		// check amount Not negative and enough
		logger.info("Consultando saldo en la cuenta de origen");
		if( accountOrigin.getBalance().signum() == -1 & 
				accountOrigin.getBalance().floatValue() < transaction.getAmount().floatValue() ) {
			throw new Exception("money in the account is not enough");
		}

		logger.info("Actualizando estados");
		accountOrigin.setBalance(  accountOrigin.getBalance().subtract(transaction.getAmount()) );
		accountDestination.setBalance( accountDestination.getBalance().add(transaction.getAmount()) );
		
		logger.info("persistiendo cuentas en db");
		
		logger.info("Cuenta origen" + accountOrigin.toString() );
		this.accountRepository.save(accountOrigin);
		
		logger.info("Cuenta destino" + accountDestination.toString() );
		this.accountRepository.save(accountDestination);
		
		AccountDTOresponse accountDTOresponse = new AccountDTOresponse();
		accountDTOresponse.setAccountDestination( accountDestination.getCbu());
		accountDTOresponse.setAccountOrigin( accountOrigin.getCbu() );

		return accountDTOresponse;
	}

	@Override
	public Account getAccountEntityByCbuOrAlias(String cbu, String alias) {
		Account account = Optional.of( this.accountRepository.findAccountByCbuOrAlias(cbu, alias) )
				.orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND,"Account not found"));	
		return account;
	}
	
	public void deleteAccount(String nroCuenta) {
		this.accountRepository.deleteAccountByAccountNumber(nroCuenta);
	}

}
