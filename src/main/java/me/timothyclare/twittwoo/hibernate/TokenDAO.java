package org.zkoss.zktwitterservice.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.zkoss.zktwitterservice.exceptions.token.TokenInvalid;
import org.zkoss.zktwitterservice.exceptions.token.TokenNotAccessible;

import twitter4j.http.AccessToken;


public class TokenDAO {
	
	private static final File tokenFile = new File("token");
	
	//TESTING ONLY
	static {
		if(tokenFile.canWrite()) tokenFile.delete();
	}
	
	public static AccessToken getToken() throws TokenInvalid, TokenNotAccessible {
		
		Object obj=null;
		FileInputStream f_in=null;
		ObjectInputStream obj_in= null;
		
		if(!tokenFile.exists()) {
			throw new TokenNotAccessible("Token hasn't been created yet");
		}
		
		if(tokenFile.canRead()) {
			try {
				// Read from disk using FileInputStream
				f_in = new FileInputStream(tokenFile);
	
				// Read object using ObjectInputStream
				obj_in = new ObjectInputStream(f_in);
	
				// Read an object
				obj = obj_in.readObject();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new TokenNotAccessible("Could not find the file", e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new TokenNotAccessible("Could not access the file", e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new TokenInvalid("Cannot initialize token", e);
			} finally {
				if((obj_in != null)) {
					try { obj_in.close(); } 
					catch (IOException e) {	e.printStackTrace(); }
				}
			}

			if (obj instanceof AccessToken)
			{
				 return (AccessToken)obj;
			} else 
				throw new TokenInvalid("Not a valid token");
		}
		
		throw new TokenNotAccessible("Could not read the file");
	}
	
	public static void setToken(AccessToken t) throws TokenNotAccessible {
		
		FileOutputStream f_out=null;
		ObjectOutputStream obj_out=null;
		
		if(!tokenFile.exists()) {
			try {
				tokenFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new TokenNotAccessible("Unable to create the token file", e);
			}
		}
			
		
		try {
			// Write to disk with FileOutputStream
			f_out = new FileOutputStream(tokenFile);
	
			// Write object with ObjectOutputStream
			obj_out = new ObjectOutputStream (f_out);
	
			// Write object out to disk
			obj_out.writeObject ( t );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new TokenNotAccessible("Token hasn't been created yet");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TokenNotAccessible("Could not access the file", e);
		} finally {
			if((obj_out != null)) {
				try { obj_out.close(); } 
				catch (IOException e) {	e.printStackTrace(); }
			}
		}
	}
}
