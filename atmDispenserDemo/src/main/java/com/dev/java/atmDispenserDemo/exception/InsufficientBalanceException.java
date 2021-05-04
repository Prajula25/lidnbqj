package com.dev.java.atmDispenserDemo.exception;

/**
 * Created by Prajula
 */
public class InsufficientBalanceException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(String message){
        super(message);
    }
}
