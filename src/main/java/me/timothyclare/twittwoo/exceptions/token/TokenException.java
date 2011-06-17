package me.timothyclare.twittwoo.exceptions.token;

public class TokenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3167369857740838296L;
	
	public TokenException(String message) {
		super(message);
	}
	
	public TokenException(String message, Exception e) {
		super(message, e);
	}
}
