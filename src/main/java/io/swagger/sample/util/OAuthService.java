package io.swagger.sample.util;

import com.sun.jersey.core.util.Base64;
import io.swagger.sample.data.SecretData;

import java.net.URLConnection;

public class OAuthService {

	public static void addBasicAuth(URLConnection urlConnection){
		String clientId = SecretData.getClientId();

		String authString = SecretData.getClientId() + ":" + SecretData.getClientSecret();
		//System.out.println("auth string: " + authString);
		byte[] authEncBytes = Base64.encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		System.out.println("Base64 encoded auth string: " + authStringEnc);

		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
	}
}
