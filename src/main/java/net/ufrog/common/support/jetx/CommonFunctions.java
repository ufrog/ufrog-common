package net.ufrog.common.support.jetx;

import jetbrick.template.runtime.JetPageContext;
import net.ufrog.common.app.App;

/**
 * @author ultrafrog
 * @version 1.0, 2014-05-15
 * @since 1.0
 */
public class CommonFunctions {

	/**
	 * @param key
	 * @return
	 */
	public static String prop(String key) {
		return App.config(key);
	}
	
	/**
	 * @param key
	 * @param args
	 * @return
	 */
	public static String msg(JetPageContext ctx, String key, String... args) {
		if (args.length > 0) {
			Object[] params = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				Object param = ctx.getContext().get(args[i]);
				if (param != null) {
					param = ctx.getContext().get(args[i]);
				} else {
					param = args[i];
				}
				params[i] = param;
			}
			return App.message(key, params);
		}
		return App.message(key);
	}
}
