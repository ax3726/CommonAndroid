package com.lm.lib_common.utils;

import java.util.UUID;

public class MyUUID {
	/**
	 * 带线的UUID
	 * @return
	 */
	public static String lineUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	/**
	 * 不带线的UUID，32位
	 * @return
	 */
	public static String nolineUUID(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)+ str.substring(24);
		return temp;
	}
	public static void main(String[] args) {
		System.out.println(nolineUUID());
	}
}
