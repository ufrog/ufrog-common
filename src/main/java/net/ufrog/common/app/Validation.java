package net.ufrog.common.app;


/**
 * 验证
 * 
 * @author ultrafrog
 * @version 1.0, 2014-08-24
 * @since 1.0
 */
public class Validation {

	/**
	 * 验证错误
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2014-08-24
	 * @since 1.0
	 */
	public static class Error {
		
		/** 错误信息 */
		private String message;
		
		/** 名称<br>对应输入项 */
		private String name;
		
		/** 构造函数 */
		public Error() {}
		
		/**
		 * 构造函数
		 * 
		 * @param message
		 * @param name
		 */
		public Error(String message, String name) {
			this.message = message;
			this.name = name;
		}
		
		/**
		 * 读取错误信息
		 * 
		 * @return
		 * @see #message
		 */
		public String getMessage() {
			return App.message(message);
		}

		/**
		 * 设置错误信息
		 * 
		 * @param message
		 * @see #message
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		/**
		 * 读取键值
		 * 
		 * @return
		 * @see #name
		 */
		public String getName() {
			return name;
		}

		/**
		 * 设置键值
		 * 
		 * @param name
		 * @see #name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * @return
		 */
		public String message() {
			return getMessage();
		}
	}
}
