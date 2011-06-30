package me.timothyclare.tawny.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

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
	
	@Id
	public String getAccount() {
		return _account;
	}
	
	public void setAccount(String account) {
		this._account = account;
	}
	
	public void setToken(byte[] token) {
		this._token = token;
	}

	public byte[] getToken() {
		return _token;
	}
}
