package me.timothyclare.tawny.dao.api;

import twitter4j.auth.AccessToken;

public interface TokenDao {
	AccessToken get(String account);
	boolean save(String account, AccessToken token);
}
