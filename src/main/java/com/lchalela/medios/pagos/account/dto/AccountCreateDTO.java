package com.lchalela.medios.pagos.account.dto;

import lombok.Data;

@Data
public class AccountCreateDTO {	
	private String name;
	private String lastName;
	private String typeAccount;
	private Long userId;
}
