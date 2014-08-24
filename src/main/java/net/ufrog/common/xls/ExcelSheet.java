package net.ufrog.common.xls;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标签注解
 * 
 * @author ultrafrog
 * @version 1.0, 2014-08-08
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelSheet {
	
	/**
	 * 名称<br>
	 * 若定义名称则忽略索引位置
	 * 
	 * @return
	 */
	public String value() default "";
	
	/**
	 * 索引<br>
	 * 仅当未定义名称时索引有效
	 * 
	 * @return
	 */
	public int index() default 0;
	
	/**
	 * 标题行数<br>
	 * 解析时自动忽略行数
	 * 
	 * @return
	 */
	public int labels() default 1;
}
