package net.ufrog.common.exception;

/**
 * 用户未登录异常
 * 
 * @author ultrafrog
 * @version 0.1, 2014-01-17
 * @since 0.1
 */
public class NotSignInException extends ServiceException {

	private static final long serialVersionUID 	= -3461737837766355183L;
	private static final String EX_SIGN_IN		= "exception.sign.in";

	/** 构造函数 */
	public NotSignInException() {
		super("user is not sign in.", EX_SIGN_IN);
	}
}
