package net.ufrog.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.ufrog.common.exception.ServiceException;

import org.apache.commons.codec.binary.Base64;

/**
 * 图片工具
 * 
 * @author ultrafrog
 * @version 1.0, 2014-02-13
 * @since 1.0
 */
public abstract class Images {

	private static final String BASE64 = "data:%s;base64,%s";
	
	/**
	 * 转换图片
	 * 
	 * @param mimeType
	 * @param bytes
	 * @return
	 */
	public static String toBase64(String mimeType, byte[] bytes) {
		return String.format(BASE64, mimeType, new String(Base64.encodeBase64(bytes)));
	}
	
	/**
	 * 转换图片
	 * 
	 * @param image
	 * @return
	 */
	public static String toBase64(File image) {
		InputStream input = null;
		try {
			input = new FileInputStream(image);
			byte[] bytes = new byte[(int) image.length()];
			input.read(bytes);
			return toBase64(Files.getMimeType(image), bytes);
		} catch (FileNotFoundException e) {
			throw new ServiceException("cannot convert '" + image.getName() + "' to base64", e);
		} catch (IOException e) {
			throw new ServiceException("cannot convert '" + image.getName() + "' to base64", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new ServiceException("input stream cannot close.", e);
				}
			}
		}
	}
}
