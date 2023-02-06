package com.lchalela.medios.pagos.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lchalela.medios.pagos.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public List<Account> findAccountByUserIdAndIsActivedTrue(Long id);
	public Account findAccountByCbuOrAliasAndIsActivedTrue(String cbu, String alias); 
}
