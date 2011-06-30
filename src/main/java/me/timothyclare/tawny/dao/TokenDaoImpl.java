package me.timothyclare.tawny.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import me.timothyclare.tawny.bean.Token;
import me.timothyclare.tawny.dao.api.TokenDao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.auth.AccessToken;

@Component
public class TokenDaoImpl implements TokenDao {
	
	SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public boolean save(String account, AccessToken t) {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(t);
			out.close();
		} catch (IOException e) {
			return false;
		} finally {
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					//TODO: log
				}
		}
		
		byte[] buffer = bos.toByteArray();
		
		Object tk = sessionFactory.getCurrentSession().get(Token.class, account);
		
		if(tk instanceof Token) {
			Token token = (Token)tk;
			token.setToken(buffer);
			sessionFactory.getCurrentSession().update(token);
		} else {
			sessionFactory.getCurrentSession().persist(new Token(account, buffer));
		}
		
		return true;
	}

	@Override
	@Transactional
	public AccessToken get(String account) {
		Session s = sessionFactory.getCurrentSession();
		Object object = s.get(Token.class, account);
		
		if(!(object instanceof Token)) {
			return null;
		}
		
		Token token = (Token)object;
		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(token.getToken()));
			AccessToken accessToken = (AccessToken)in.readObject();
			return accessToken;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
