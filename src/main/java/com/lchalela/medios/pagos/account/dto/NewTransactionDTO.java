package com.lchalela.medios.pagos.account.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewTransactionDTO {
	@Length(min=22 , max=22)
	private String accountOrigin;
	private String aliasDestination;
	@Length(min=22 , max=22)
	private String cbuDestination;
	private String reason;
	@Min(value = 1,message = "The minimum amount required is $1.")
	private BigDecimal amount;
}
