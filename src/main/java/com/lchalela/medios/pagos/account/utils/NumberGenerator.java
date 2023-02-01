package com.lchalela.medios.pagos.account.utils;

public class NumberGenerator {
	public static String generateNumbers(int max) {
		String number = "";
		while(number.length() < max) {
			number +=  String.valueOf( (int) Math.floor( Math.random() * 90L + 10L));
		}
		return number ;
	}
}
