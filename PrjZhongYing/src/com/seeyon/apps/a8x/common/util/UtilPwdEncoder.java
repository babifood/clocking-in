package com.seeyon.apps.a8x.common.util;

public final class UtilPwdEncoder {

	public static String decode(String text) {
		if (text.startsWith("/1.0/")) {
			return decodeString(text.substring("/1.0/".length()));
		}
		return text;
	}

	public static String decodeString(String encodeString) {
		if (encodeString == null) {
			return null;
		}
		String text = null;
		try {
			text = new String(UtilBase64.decode(encodeString.getBytes("UTF-8")), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		char[] encodeStringCharArray = text.toCharArray();
		for (int i = 0; i < encodeStringCharArray.length; i++) {
			int tmp56_55 = i;
			char[] tmp56_54 = encodeStringCharArray;
			tmp56_54[tmp56_55] = ((char) (tmp56_54[tmp56_55] - '\001'));
		}
		return new String(encodeStringCharArray);
	}
	
}
