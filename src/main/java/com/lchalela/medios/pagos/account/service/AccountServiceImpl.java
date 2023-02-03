package com.lchalela.medios.pagos.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.lchalela.medios.pagos.account.client.TransactionRest;
import com.lchalela.medios.pagos.account.dto.AccountCompletDTO;
import com.lchalela.medios.pagos.account.dto.AccountCreateDTO;
import com.lchalela.medios.pagos.account.dto.AccountDTOresponse;
import com.lchalela.medios.pagos.account.dto.AccountResponseDTO;
import com.lchalela.medios.pagos.account.dto.CbuDTO;
import com.lchalela.medios.pagos.account.dto.NewTransactionDTO;
import com.lchalela.medios.pagos.account.dto.TransactionDTO;
import com.lchalela.medios.pagos.account.mapper.AccountMapper;
import com.lchalela.medios.pagos.account.model.Account;
import com.lchalela.medios.pagos.account.repository.AccountRepository;
import static com.lchalela.medios.pagos.account.utils.NumberGenerator.generateNumbers;;

@Service
public class AccountServiceImpl implements AccountService {

	private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	private AccountRepository accountRepository;
	private AccountMapper accountMapper;
	private TransactionRest transactionRest;
	
	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository,
			AccountMapper accountMapper,TransactionRest transactionRest) {
		this.accountRepository = accountRepository;
		this.accountMapper = accountMapper;
		this.transactionRest = transactionRest;
	}

	@Override
	public List<AccountCompletDTO> getAccountByUserID(Long id) throws Exception {	
		
		logger.info("init find account by user id");
		
		List<Account> accountDTO = this.accountRepository.findAccountByUserIdAndIsActivedTrue(id);

		try {
			
			for (Account account : accountDTO) {		
				CbuDTO cbuDTO = new CbuDTO();
				cbuDTO.setAccountOrigin( account.getCbu() );
				List<TransactionDTO> transactions = 
						this.transactionRest.getAllTransaction( cbuDTO );
				account.setTransactionDTO(transactions);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.info("return account");
		return this.accountMapper.accountToAccountCompleteDTO( accountDTO );
	}

	@Override
	public AccountResponseDTO getAccountByCbuOrAlias(String cbu, String alias) throws Exception {
		logger.info("get account by cbu or alias -> cbu:".concat(cbu).concat(" alias: ").concat(alias) );
		Account account = Optional.of( this.accountRepository.findAccountByCbuOrAliasAndIsActivedTrue(cbu, alias))
				.orElseThrow( 
						() -> {
							logger.error("Account not found -> cbu:".concat(cbu).concat(" alias:").concat(alias) );
							return new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found");						
						}
						);
		logger.info("return account");
		return this.accountMapper.accountToAccountResponseDTO(account);
	}

	@Override
	public List<AccountCompletDTO> createAccount(AccountCreateDTO accountCreateDTO) {
		
		logger.info("init register new account");
		
		Account account = new Account();
		
		logger.info("set account ");
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

		logger.info("return list DTO");
		return this.accountMapper
				.accountToAccountCompleteDTO( List.of( this.accountRepository.save( account)) );
	}

	@Override
	@Transactional
	public AccountDTOresponse transferByCbuOrAlias(NewTransactionDTO transaction) throws Exception {
		
		logger.info("Checks accounts");
		Account accountOrigin = this.getAccountEntityByCbuOrAlias(transaction.getAccountOrigin(), "");
		
		Account accountDestination = this.getAccountEntityByCbuOrAlias(
										transaction.getCbuDestination(),
										transaction.getAliasDestination());
		
		if(accountOrigin.getIsActived() != true) {
			throw new Exception("The account origin is not actived");
		}
		
		if(accountDestination.getIsActived() != true) {
			throw new Exception("The account destinatiton is not actived");
		}
		
		//TODO: Validar que este dentro del dia
		
		logger.info("check amount Not negative and enough");
		if( accountOrigin.getBalance().signum() == -1 & 
				accountOrigin.getBalance().floatValue() < transaction.getAmount().floatValue() ) {
			throw new Exception("money in the account is not enough");
		}

		logger.info("update balance in the accounts");
		accountOrigin.setBalance(  accountOrigin.getBalance().subtract(transaction.getAmount()) );
		accountDestination.setBalance( accountDestination.getBalance().add(transaction.getAmount()) );
		
		logger.info("update account in db");
		this.accountRepository.save(accountOrigin);
		this.accountRepository.save(accountDestination);
			
		logger.info("Return cbu origin and destination");
		AccountDTOresponse accountDTOresponse = new AccountDTOresponse();
		accountDTOresponse.setAccountDestination( accountDestination.getCbu());
		accountDTOresponse.setAccountOrigin( accountOrigin.getCbu() );
		return accountDTOresponse;
	}

	@Override
	public Account getAccountEntityByCbuOrAlias(String cbu, String alias) {
		//TODO: return account enabled
		logger.info("Init get Entity by cbu or alias cbu: ".concat(cbu).concat(" alias: ".concat(alias)));
		Account account = Optional.of( this.accountRepository.findAccountByCbuOrAliasAndIsActivedTrue(cbu, alias) )
				.orElseThrow( () 
						->
				{
					logger.error("Account not found by cbu: ".concat(cbu).concat(" alias: ").concat(alias) );
					return new ResponseStatusException( HttpStatus.NOT_FOUND,"Account not found");
				}
						);	
		return account;
	}
	
	public void deleteAccount(String nroCuenta) {
		//TODO: add soft deleted
		this.accountRepository.deleteAccountByAccountNumber(nroCuenta);
	}

}
