package com.lchalela.medios.pagos.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AmountNotEnoughException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 442806330471613470L;

	public AmountNotEnoughException() {super();}
	
	public AmountNotEnoughException (String message) {
		super(message);
	}
}
