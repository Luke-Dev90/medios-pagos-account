package com.lchalela.medios.pagos.account.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCompletDTO {
	private BigDecimal balance;
	private String typeAccount;
	private String accountNumber;
	private String cbu;
	private String alias;
	private Long userId;
	private Boolean isActived;
	private List<TransactionDTO> transactionDTO;
}
