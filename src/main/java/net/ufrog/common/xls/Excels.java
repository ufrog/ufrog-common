package net.ufrog.common.xls;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Excels
 * 
 * @author ultrafrog
 * @version 1.0, 2014-07-20
 * @since 1.0
 */
public abstract class Excels {

	/**
	 * 解析
	 * 
	 * @param is
	 * @param requiredType
	 * @return
	 */
	public static <T extends Serializable> List<T> parse(InputStream is, Class<T> requiredType) {
//		try {
//			Workbook workbook = new XSSFWorkbook(is);
//			Sheet sheet = workbook.getSheetAt(0);
//		} catch (IOException e) {
//			throw new ServiceException("cannot parse xls file.");
//		}
		return null;
	}
	
	
}
