package me.timothyclare.tawny.dao.types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import me.timothyclare.tawny.Messages;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import twitter4j.auth.AccessToken;

public class TwitterAccessTokenType implements UserType {

	private static final int[] SQL_TYPES = { Types.BLOB };

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return AccessToken.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		} 
		
		boolean cont = (x instanceof AccessToken) && (y instanceof AccessToken);
		
		if(cont) {
			cont = ((AccessToken)x).equals(y);
		}
		
		return cont;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		if(!(x instanceof AccessToken)) return 0;
		
		return ((AccessToken)x).hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		
		AccessToken accessToken = null;
		byte[] bytes = rs.getBytes(names[0]);
		
		if(!rs.wasNull()) {
			accessToken = readAccessToken(bytes);
		}
		
		return accessToken;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if(!(value instanceof AccessToken)) {
			st.setBytes(index, new byte[]{0});
		} else {
			AccessToken accessToken = (AccessToken)value;
			byte[] bytes = writeAccessToken(accessToken);
			st.setBytes(index, bytes);
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {		
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		if(!(value instanceof AccessToken)) {
			throw new UnsupportedOperationException(me.timothyclare.tawny.Messages.getString("TwitterAccessTokenType.0") + value.getClass());
		}
		
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}
	
	private AccessToken readAccessToken(byte[] accessToken) {
		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(accessToken));
			AccessToken aToken = (AccessToken)in.readObject();
			return aToken;
		} catch (IOException e) {
			//TODO: log
			throw new HibernateException(Messages.getString("TwitterAccessTokenType.1"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new HibernateException(Messages.getString("TwitterAccessTokenType.1"));
		}
	}
	
	private byte[] writeAccessToken(AccessToken at) {
		
		byte[] buffer = null;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(at);
			out.close();
			
			buffer = bos.toByteArray();
		} catch (IOException e) {
			//return false; TODO: log
			throw new HibernateException(Messages.getString("TwitterAccessTokenType.1"));
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					//TODO: log
				}
			}
		}
		
		return buffer;
		
	}

}
