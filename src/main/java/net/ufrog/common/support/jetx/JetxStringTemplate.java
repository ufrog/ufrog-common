package net.ufrog.common.support.jetx;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import jetbrick.template.JetContext;
import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebEngineLoader;
import net.ufrog.common.Logger;
import net.ufrog.common.exception.ServiceException;

/**
 * @author ultrafrog
 * @version 1.0, 2014-05-15
 * @since 1.0
 */
public class JetxStringTemplate {

	private static Map<String, JetTemplate> templates = new HashMap<String, JetTemplate>();
	
	/**
	 * @param key
	 * @param source
	 * @return
	 */
	public static JetTemplate getTemplate(String key, String source) {
		if (!templates.containsKey(key)) {
			templates.put(key, JetWebEngineLoader.getJetEngine().createTemplate(source));
			Logger.info("initialize template '%s'", key);
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
	 * @param source
	 * @param param
	 * @return
	 */
	public static String render(String key, String source, Object... params) {
		// 判断参数个数
		if (params.length % 2 != 0) {
			throw new ServiceException("params is not pair.");
		}
		
		// 处理参数
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < params.length; i++) {
			map.put(String.valueOf(params[i++]), params[i]);
		}
		
		// 处理并返回结果
		return render(key, source, map);
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
		System.out.println(render("test", "${hello}, ${1+2+4}", "hellp", "world"));
	}
}
