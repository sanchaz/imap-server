package main;

import java.lang.reflect.Array;

public class Parsing {

	public boolean login(String a) {
		String[] s = a.split(" ");
		if(s[1].compareTo("LOGIN") == 0) {
			return true;		
		} else {
			return false;
		}
	}
	
	public boolean loginOK(String a) {
		String[] s = a.split(" ");
		if(s[1].compareTo("OK") == 0) {
			return true;		
		} else {
			return false;
		}	
	}
	
	public String[] getUserPass(String a) {
		String[] s = a.split(" ");
		String[] s2 = new String[2];
		s2[0] = s[2];
		s2[1] = s[3];
		return s2;
	}
	
	public static Parsing Parser = new Parsing();
}
