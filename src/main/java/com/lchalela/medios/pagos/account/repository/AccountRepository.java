package com.lchalela.medios.pagos.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lchalela.medios.pagos.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findAccountByUserId(Long id);
	public Account findAccountByCbuOrAlias(String cbu, String alias); 
}
