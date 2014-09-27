package net.ufrog.common.utils;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import net.ufrog.common.Logger;
import net.ufrog.common.utils.Cryptos.HashType;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * 密码工具类
 * 
 * @author ultrafrog
 * @version 1.0, 2014-09-27
 * @since 1.0
 */
public abstract class Passwords {

	protected static SecureRandom secureRandom = new SecureRandom();
	
	/**
	 * 密码加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		byte[] salt = new byte[8];
		secureRandom.nextBytes(salt);
		return encode(str, salt);
	}
	
	/**
	 * 密码匹配
	 * 
	 * @param raw
	 * @param encoded
	 * @return
	 */
	public static Boolean match(String raw, String encoded) {
		try {
			byte[] digest = Hex.decodeHex(encoded.toCharArray());
			byte[] salt = sub(digest, 0, 8);
			return Strings.equals(encoded, encode(raw, salt));
		} catch (DecoderException e) {
			Logger.warn(e.getMessage());
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 加密
	 * 
	 * @param str
	 * @param salt
	 * @return
	 */
	protected static String encode(String str, byte[] salt) {
		byte[] digest = Cryptos.hash(concatenate(salt, str.getBytes(Charset.forName("utf-8"))), HashType.SHA256);
		return new String(Hex.encodeHex(concatenate(salt, digest)));
	}
	
	/**
	 * 合并字节数组
	 * 
	 * @param arrays
	 * @return
	 */
	protected static byte[] concatenate(byte[]... arrays) {
		// 初始化
		Integer length = 0;
		for (byte[] array: arrays) length += array.length;
		byte[] dest = new byte[length];
		
		// 循环合并
		Integer destPos = 0;
		for (byte[] array: arrays) {
			System.arraycopy(array, 0, dest, destPos, array.length);
			destPos += array.length;
		}
		
		// 返回结果
		return dest;
	}
	
	/**
	 * @param bytes
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	protected static byte[] sub(byte[] array, Integer beginIndex, Integer endIndex) {
		Integer length = endIndex - beginIndex;
		byte[] sub = new byte[length];
		System.arraycopy(array, beginIndex, sub, 0, length);
		return sub;
	}
	
	/**
	 * @param args
	 */
	public static void main(String... args) {
		String encoded = encode("admin");
		System.out.println(encoded);
		System.out.println(encoded.length());
		System.out.println(match("admin", encoded));
	}
}
