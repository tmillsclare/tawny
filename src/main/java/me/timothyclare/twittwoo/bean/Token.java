package me.timothyclare.twittwoo.bean;

import javax.persistence.Entity;

@Entity
public class Token implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2410588864024483363L;
	
	private String _account;
	private byte[] _token;
	
	public Token() {}
	public Token(String account, byte[] token) {this._account = account; this._token = token;}
	
	public String getAccount() {
		return _account;
	}
	public void setId(String id) {
		this._account = id;
	}
	public void setToken(byte[] token) {
		this._token = token;
	}

	public byte[] getToken() {
		return _token;
	}
}
