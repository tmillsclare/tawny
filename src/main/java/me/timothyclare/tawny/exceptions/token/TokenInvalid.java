package me.timothyclare.tawny.exceptions.token;

public class TokenInvalid extends TokenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8354342504692686055L;
	
	public TokenInvalid(String message) {
		super(message);
	}
	
	public TokenInvalid(String message, Exception e) {
		super(message, e);
	}
}
