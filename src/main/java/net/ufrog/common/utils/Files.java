package net.ufrog.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ufrog.common.exception.ServiceException;

/**
 * 文件工具
 * 
 * @author ultrafrog
 * @version 1.0, 2011-12-30
 * @since 1.0
 */
public abstract class Files {

	private static final Pattern PATTERN_EXT 	= Pattern.compile("^.*\\.([^.]+)$");
	private static final String PATH_MIMETYPES	= "mimetypes.properties";
	
	private static Properties mimeTypes;
	
	/**
	 * 读取文件
	 * 
	 * @param path
	 * @return
	 */
	public static File getFile(String path) {
		return new File(Files.class.getResource(path).getPath());
	}
	
	/**
	 * 读取输入流
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream getInputStream(String path) {
		return Files.class.getResourceAsStream(path);
	}
	
	/**
	 * 读取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExt(String filename) {
		Matcher matcher = PATTERN_EXT.matcher(filename);
		if (matcher.matches()) return matcher.group(1);
		return "";
	}
	
	/**
	 * 读取文件扩展名
	 * 
	 * @param file
	 * @return
	 */
	public static String getExt(File file) {
		return getExt(file.getName());
	}
	
	/**
	 * 读取扩展类型
	 * 
	 * @return
	 */
	public static Properties getMimeTypes() {
		if (mimeTypes == null) {
			try {
				mimeTypes = new Properties();
				mimeTypes.load(getInputStream(PATH_MIMETYPES));
			} catch (IOException e) {
				throw new ServiceException("cannot load '" + PATH_MIMETYPES + "'.", e);
			}
		}
		return mimeTypes;
	}
	
	/**
	 * 读取扩展类型
	 * 
	 * @param filename
	 * @return
	 */
	public static String getMimeType(String filename) {
		return getMimeTypes().getProperty(getExt(filename));
	}
	
	/**
	 * 读取扩展类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getMimeType(File file) {
		return getMimeType(file.getName());
	}
	
	/**
	 * 转换成字节数组
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] toBytes(File file) {
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			byte[] bytes = new byte[Long.valueOf(file.length()).intValue()];
			input.read(bytes);
			return bytes;
		} catch (FileNotFoundException e) {
			throw new ServiceException("cannot to bytes.", e);
		} catch (IOException e) {
			throw new ServiceException("cannot to bytes.", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new ServiceException("cannot close input stream.", e);
				}
			}
		}
	}
	
	/**
	 * 转换成字节数组
	 * 
	 * @param path
	 * @return
	 */
	public static byte[] toBytes(String path) {
		return toBytes(getFile(path));
	}
}
