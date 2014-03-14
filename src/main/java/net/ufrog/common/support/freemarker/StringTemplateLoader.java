package net.ufrog.common.support.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Strings;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 字符串模板加载器
 * 
 * @author ultrafrog
 * @version 1.0, 2014-03-13
 * @since 1.0
 */
public class StringTemplateLoader implements TemplateLoader {

	private static Configuration configuration;
	
	static {
		configuration = new Configuration();
		configuration.setTemplateLoader(new StringTemplateLoader());
		configuration.setLocalizedLookup(false);
		configuration.setDefaultEncoding("utf-8");
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#findTemplateSource(java.lang.String)
	 */
	@Override
	public Object findTemplateSource(String name) throws IOException {
		return new StringReader(Strings.empty(name, ""));
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#closeTemplateSource(java.lang.Object)
	 */
	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		StringReader.class.cast(templateSource).close();
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#getLastModified(java.lang.Object)
	 */
	@Override
	public long getLastModified(Object templateSource) {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see freemarker.cache.TemplateLoader#getReader(java.lang.Object, java.lang.String)
	 */
	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return Reader.class.cast(templateSource);
	}
	
	/**
	 * 解析模板
	 * 
	 * @param html
	 * @param root
	 * @return
	 */
	public static String process(String html, Map<String, Object> root) {
		try {
			Template template = configuration.getTemplate(html);
			StringWriter writer = new StringWriter();
			template.process(root, writer);
			return writer.toString();
		} catch (IOException e) {
			throw new ServiceException("cannot parse template.", "exception.freemarker.process", e);
		} catch (TemplateException e) {
			throw new ServiceException("cannot parse template.", "exception.freemarker.process", e);
		}
	}
}
