package net.ufrog.common.support.jetx;

import jetbrick.template.runtime.JetTagContext;

/**
 * @author ultrafrog
 * @version 1.0, 2014-05-15
 * @since 1.0
 */
public class CommonTags {

	/**
	 * @param ctx
	 * @param name
	 */
	public static void set(JetTagContext ctx, String name) {
		ctx.getContext().put(name, ctx.getBodyContent());
	}
}
