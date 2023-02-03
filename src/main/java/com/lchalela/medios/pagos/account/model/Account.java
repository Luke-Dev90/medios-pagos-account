package com.lchalela.medios.pagos.account.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lchalela.medios.pagos.account.dto.TransactionDTO;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal balance;
	private String typeAccount;
	private String accountNumber;
	private String cbu;
	private String alias;
	private Long userId;
	@Transient
	private List<TransactionDTO> transactionDTO;
	private Boolean isActived;
	
	@PrePersist
	public void newAccount() {
		isActived = true;
	}
}
