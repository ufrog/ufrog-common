package net.ufrog.common.support.jetx;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import jetbrick.template.JetContext;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

/**
 * @author ultrafrog
 * @version 1.0, 2014-05-15
 * @since 1.0
 */
public class StringTemplate {

	private static Map<String, JetTemplate> templates = new HashMap<String, JetTemplate>();
	
	/**
	 * @param key
	 * @param source
	 * @return
	 */
	public static JetTemplate getTemplate(String key, String source) {
		if (!templates.containsKey(key)) {
			templates.put(key, JetEngine.create().createTemplate(source));
		}
		return templates.get(key);
	}
	
	/**
	 * @param key
	 * @param source
	 * @param map
	 * @return
	 */
	public static String render(String key, String source, Map<String, Object> map) {
		JetTemplate template = getTemplate(key, source);
		JetContext context = new JetContext(map);
		Writer writer = new StringWriter();
		
		template.render(context, writer);
		return writer.toString();
	}
	
	/**
	 * @param key
	 */
	public static void clear(String key) {
		templates.remove(key);
	}
	
	/**
	 * @param args
	 */
	public static void main(String... args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hello", "world");
		System.out.println(render("test", "${hello}, ${1+2+4}", map));
	}
}
