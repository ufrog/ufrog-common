package net.ufrog.common.utils;

import java.util.UUID;

import net.ufrog.common.utils.Cryptos.HashType;

/**
 * 编码工具
 * 
 * @author ultrafrog
 * @version 1.0, 2012-10-26
 * @since 1.0
 */
public abstract class Codecs {

	/**
	 * UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * MD5
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return Cryptos.hashAndHex(str, HashType.MD5);
	}
}
