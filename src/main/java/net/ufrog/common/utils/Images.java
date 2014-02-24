package net.ufrog.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import net.ufrog.common.app.App;
import net.ufrog.common.app.WebApp;
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
	
	/**
	 * 修改图片尺寸
	 * 
	 * @param input
	 * @param mimeType
	 * @param to
	 * @param width
	 * @param height
	 * @param keepRatio
	 */
	public static void resize(InputStream input, String mimeType, File to, Integer width, Integer height, Boolean keepRatio) {
		try {
			// 初始化
			BufferedImage source = ImageIO.read(input);
			Integer fwidth = source.getWidth();
			Integer fheight = source.getHeight();
			Double ratio = fwidth.doubleValue() / fheight.doubleValue();
			Integer mwidth = width;
			Integer mheight = height;
			
			// 判断并确定宽高
			if (width < 0 && height < 0) { width = fwidth; height = fheight; }
			if (width < 0 && height > 0) { width = Double.valueOf(height * ratio).intValue(); }
			if (width > 0 && height < 0) { height = Double.valueOf(width / ratio).intValue(); }
			if (keepRatio) {
				height = Double.valueOf(width / ratio).intValue();
				if (height > mheight) { height = mheight; width = Double.valueOf(height * ratio).intValue(); }
				if (width > mwidth) { width = mwidth; height = Double.valueOf(width / ratio).intValue(); }
			}
			
			// 生成新图片
			BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Image resized = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			Graphics graphics = dest.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, width, height);
			graphics.drawImage(resized, 0, 0, null);
			
			ImageWriter writer = ImageIO.getImageWritersByMIMEType(mimeType).next();
			ImageWriteParam params = writer.getDefaultWriteParam();
			FileImageOutputStream fios = new FileImageOutputStream(to);
			writer.setOutput(fios);
			
			IIOImage img = new IIOImage(dest, null, null);
			writer.write(null, img, params);
			fios.flush();
			fios.close();
		} catch (IOException e) {
			throw new ServiceException("cannot resize image.", e);
		}
	}
	
	/**
	 * 修改图片尺寸
	 * 
	 * @param input
	 * @param mimeType
	 * @param to
	 * @param width
	 * @param height
	 */
	public static void resize(InputStream input, String mimeType, File to, Integer width, Integer height) {
		resize(input, mimeType, to, width, height, Boolean.FALSE);
	}
	
	/**
	 * 修改图片尺寸
	 * 
	 * @param image
	 * @param to
	 * @param width
	 * @param height
	 * @param keepRatio
	 */
	public static void resize(File image, File to, Integer width, Integer height, Boolean keepRatio) {
		FileInputStream input = null;
		try {
			input = new FileInputStream(image);
			resize(input, Files.getMimeType(image), to, width, height, keepRatio);
		} catch (FileNotFoundException e) {
			throw new ServiceException("cannot resize image.", e);
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
	 * 修改图片尺寸
	 * 
	 * @param image
	 * @param to
	 * @param width
	 * @param height
	 */
	public static void resize(File image, File to, Integer width, Integer height) {
		resize(image, to, width, height, Boolean.FALSE);
	}
	
	/**
	 * 修改图片尺寸
	 * 
	 * @param input
	 * @param mimeType
	 * @param width
	 * @param height
	 * @param keepRatio
	 * @return
	 */
	public static byte[] resize(InputStream input, String mimeType, Integer width, Integer height, Boolean keepRatio) {
		File to = new File(App.current(WebApp.class).getTempPath() + "/" + Codecs.uuid());
		resize(input, mimeType, to, width, height, keepRatio);
		byte[] bytes = Files.toBytes(to);
		to.delete();
		return bytes;
	}

	/**
	 * 修改图片尺寸
	 * 
	 * @param input
	 * @param mimeType
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] resize(InputStream input, String mimeType, Integer width, Integer height) {
		return resize(input, mimeType, width, height, Boolean.FALSE);
	}

	/**
	 * 裁剪图片
	 * 
	 * @param input
	 * @param mimeType
	 * @param to
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void crop(InputStream input, String mimeType, File to, Integer x, Integer y, Integer width, Integer height) {
		try {
			// 初始化
			BufferedImage source = ImageIO.read(input);
			BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			// 生成新图片
			Image cropped = source.getSubimage(x, y, width, height);
			Graphics graphics = dest.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, width, height);
			graphics.drawImage(cropped, 0, 0, null);
			
			ImageWriter writer = ImageIO.getImageWritersByMIMEType(mimeType).next();
			ImageWriteParam params = writer.getDefaultWriteParam();
			FileImageOutputStream fios = new FileImageOutputStream(to);
			writer.setOutput(fios);
			
			IIOImage img = new IIOImage(dest, null, null);
			writer.write(null, img, params);
			fios.flush();
			fios.close();
		} catch (IOException e) {
			throw new ServiceException("cannot crop image.", e);
		}
	}
	
	/**
	 * 裁剪图片
	 * 
	 * @param image
	 * @param to
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void crop(File image, File to, Integer x, Integer y, Integer width, Integer height) {
		FileInputStream input = null;
		try {
			input = new FileInputStream(image);
			crop(input, Files.getMimeType(image), to, x, y, width, height);
		} catch (FileNotFoundException e) {
			throw new ServiceException("cannot crop image.", e);
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
	 * 裁剪图片
	 * 
	 * @param input
	 * @param mimeType
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static byte[] crop(InputStream input, String mimeType, Integer x, Integer y, Integer width, Integer height) {
		File to = new File(App.current(WebApp.class).getTempPath() + "/" + Codecs.uuid());
		crop(input, mimeType, to, x, y, width, height);
		byte[] bytes = Files.toBytes(to);
		to.delete();
		return bytes;
	}
}
