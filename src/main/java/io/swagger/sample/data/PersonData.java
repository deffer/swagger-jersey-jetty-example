package io.swagger.sample.data;

import io.swagger.sample.model.Profile;

import java.util.HashMap;
import java.util.Map;


public class PersonData {

	static Map<String, Profile> data = new HashMap<String, Profile>();

	public static Profile PATRICK = new Profile("Patrick", "Nom", "pck123", "pnom@aucklang.ac.nz");
	public static Profile KEN = new Profile("Ken", "To", "ken123", "kento@aucklang.ac.nz");
	public static Profile BOB = new Profile("Bob", "Builder", "bob123", "bobb@aucklang.ac.nz");

	static String operator = PATRICK.getUpi();
	static String me = KEN.getUpi();

	static{
		data.put(PATRICK.getUpi(), PATRICK);
		data.put(KEN.getUpi(), KEN);
		data.put(BOB.getUpi(), BOB);
	}

	public static Profile replace(Profile p) {
		if (p!=null)
			return data.put(p.getUpi(), p);
		else
			return null;
	}

	public static boolean delete(String upi){
		return data.remove(upi)!=null;
	}

	public static Profile get(String upi){
		return data.get(upi);
	}

	public static Profile getOperator(){
		Profile p = data.get(operator);
		return p!=null? p : PATRICK;
	}

	public static Profile getMe(){
		Profile p = data.get(me);
		return p!=null? p : PATRICK;
	}

	public static String setOperator(String newOperator){
		String result = operator;
		operator = newOperator;
		return result;
	}

	public static String setMe(String myUpi){
		String result = me;
		me = myUpi;
		return result;
	}
}
