package io.swagger.sample.data;

import java.util.HashMap;
import java.util.Map;

public class SecretData {
	static final String CLIENT_ID = "client_id";
	static final String CLIENT_PW = "client_secret";

	static Map<String, String> secrets = new HashMap<String, String>();

	public static String getSecret(String name){
		return secrets.get(name);
	}

	public static String putSecret(String name, String value){
		return secrets.put(name, value);
	}

	public static void setClientCredentials(String clientId, String clientSecret){
		secrets.put(CLIENT_ID, clientId);
		secrets.put(CLIENT_PW, clientSecret);
	}

	public static String getClientId(){
		return secrets.get(CLIENT_ID);
	}

	public static String getClientSecret(){
		return secrets.get(CLIENT_PW);
	}
}
