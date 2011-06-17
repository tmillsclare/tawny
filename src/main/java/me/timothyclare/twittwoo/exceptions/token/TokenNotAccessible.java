package me.timothyclare.twittwoo.exceptions.token;


public class TokenNotAccessible extends TokenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3838435069639367060L;

	public TokenNotAccessible(String string, Exception e) {
		super(string, e);
	}

	public TokenNotAccessible(String string) {
		super(string);
	}

}
