package me.barry1990.utils;


public final class Converter {
	
	final private static String hexString = "0123456789ABCDEF";
	final private static char[] hexArray = hexString.toCharArray();
	public static String shortsToHex(short[] bytes) {
		char[] hexChars = new char[bytes.length * 4];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFFFF;
			hexChars[j * 4] = hexArray[(v & 0xF000) >> 12];
			hexChars[j * 4 + 1] = hexArray[(v & 0xF00) >> 8];
			hexChars[j * 4 + 2] = hexArray[(v & 0xF0) >> 4];
			hexChars[j * 4 + 3] = hexArray[v & 0xF];
		}
		return new String(hexChars);
	}
	
	
	public static short[] hexToShort(String hex) {
		
		short[] hexChars = new short[hex.length() / 4];
		for (int i = 0; i < hexChars.length; i++) {
			int a = hexString.indexOf(hex.charAt(i*4));
			int b = hexString.indexOf(hex.charAt(i*4+1));
			int c = hexString.indexOf(hex.charAt(i*4+2));
			int d = hexString.indexOf(hex.charAt(i*4+3));
			hexChars[i] =  (short) ((((a << 12) & 0xF000) | ((b << 8) & 0xF00) | ((c << 4) & 0xF0) | (d & 0xF)) & 0xFFFF) ;
			
		}
		return hexChars;
		
	}	
	
	public static String halfBytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length];
		for ( int j = 0; j < bytes.length; j++ ) {
			hexChars[j] = hexArray[bytes[j] & 0xF];
		}
		return new String(hexChars);
	}
	
	
	public static byte[] hexToHalfBytes(String hex) {
		
		byte[] hexChars = new byte[hex.length()];
		for (int i = 0; i < hexChars.length; i++) {
			hexChars[i] =  (byte) hexString.indexOf(hex.charAt(i));				
		}
		return hexChars;
		
	}
	
}