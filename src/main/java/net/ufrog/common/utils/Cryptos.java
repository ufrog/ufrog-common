package net.ufrog.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.ufrog.common.Logger;
import net.ufrog.common.app.App;
import net.ufrog.common.exception.ServiceException;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密工具
 * 
 * @author ultrafrog
 * @version 1.0, 2011-12-19
 * @since 1.0
 */
public abstract class Cryptos {

	private static final String CONF_SECRET 	= "app.secret";
	
	/**
	 * 散列字符串
	 * 
	 * @param str
	 * @param hashType
	 * @return
	 */
	public static byte[] hash(String str, HashType hashType) {
		try {
			MessageDigest md = MessageDigest.getInstance(hashType.toString());
			return md.digest(str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			Logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 将二进制转换成十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String hex(byte[] bytes) {
		String hex = "0123456789abcdef";
		StringBuffer out = Strings.buffer(bytes.length);
		for (byte b: bytes) {
			out.append(hex.charAt(b >>> 4 & 0x0F));
			out.append(hex.charAt(b & 0x0F));
		}
		return out.toString();
	}
	
	/**
	 * 将散列结果转换成十六进制字符串
	 * 
	 * @param str
	 * @param hashType
	 * @return
	 */
	public static String hashAndHex(String str, HashType hashType) {
		return hex(hash(str, hashType));
	}
	
	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		return encrypt(str, App.config(CONF_SECRET).substring(0, 16));
	}
	
	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String encrypt(String str, String key) {
		try {
			byte raw[] = key.getBytes();
			SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return Base64.encodeBase64String(cipher.doFinal(str.getBytes()));
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new ServiceException("can not encrypt '" + str + "'");
		}
	}
	
	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String decrypt(String str) {
		return decrypt(str, App.config(CONF_SECRET).substring(0, 16));
	}
	
	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String decrypt(String str, String key) {
		try {
			byte raw[] = key.getBytes();
			SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(Base64.decodeBase64(str)));
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new ServiceException("can not decrypt '" + str + "'");
		}
	}
	
	/**
	 * 散列类型
	 * 
	 * @author ultrafrog
	 * @since 1.0
	 * @version 1.0
	 */
	public enum HashType {
		
		MD5("MD5"),
		SHA1("SHA-1"),
		SHA256("SHA-256"),
		SHA512("SHA-512");
		
		/** 运算法则 */
		private String algorithm;
		
		/**
		 * 构造函数
		 * 
		 * @param algorithm
		 */
		HashType(String algorithm) {
			this.algorithm = algorithm;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.algorithm;
		}
	}
}
