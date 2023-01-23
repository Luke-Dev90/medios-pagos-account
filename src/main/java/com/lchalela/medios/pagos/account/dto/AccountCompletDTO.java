package com.lchalela.medios.pagos.account.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccountCompletDTO {
	private BigDecimal balance;
	private String typeAccount;
	private String cbu;
	private String alias;
	private Long userId;
	private TransactionDTO transactionDTO;
}
