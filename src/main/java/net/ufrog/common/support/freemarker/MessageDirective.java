package net.ufrog.common.support.freemarker;

import java.io.IOException;
import java.util.Map;

import net.ufrog.common.app.App;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 消息指令<br>
 * 读取国际化消息
 * 
 * @author ultrafrog
 * @version 1.0, 2014-03-03
 * @since 1.0
 */
public class MessageDirective implements TemplateDirectiveModel {

	private static final String PARAM_KEY	= "key";
	
	/* (non-Javadoc)
	 * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		if (!params.containsKey(PARAM_KEY)) throw new TemplateModelException("key is necessary!");
		env.getOut().write(App.message(params.get(PARAM_KEY).toString()));
	}
}
