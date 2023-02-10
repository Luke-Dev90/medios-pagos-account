package com.lchalela.medios.pagos.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountInactived extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4727971212563423967L;

	public AccountInactived() { super();}
	
	public AccountInactived(String message) {
		super(message);
	}
	
}
