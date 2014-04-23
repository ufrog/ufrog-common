package net.ufrog.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络工具
 * 
 * @author ultrafrog
 * @version 1.0, 2012-11-30
 * @since 1.0
 */
public abstract class Networks {

	public static String removeTag(String html) {
		Pattern pattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(html);
		return matcher.replaceAll("");
	}
}
