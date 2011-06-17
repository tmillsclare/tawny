package me.timothyclare.twittwoo.bean;


public class Token implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2410588864024483363L;
	
	private String token;
	
	public Token() {}
	public Token(String token) {this.token = token;}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}
