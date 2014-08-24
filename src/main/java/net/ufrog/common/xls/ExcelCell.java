package net.ufrog.common.xls;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 单元格注解
 * 
 * @author ultrafrog
 * @version 1.0, 2014-08-08
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCell {
	
	/**
	 * 字段名称
	 * 
	 * @return
	 */
	public String value() default "";
	
	/**
	 * 字段索引<br>
	 * 确定对应文件字段
	 * 
	 * @return
	 */
	public int index();
}
