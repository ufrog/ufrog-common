package net.ufrog.common.xls;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ultrafrog
 * @version 1.0, 2014-07-21
 * @since 1.0
 */
@ExcelSheet
public class SampleExcelSheet implements Serializable {

	private static final long serialVersionUID = 2046087405562794832L;

	/** 名称 */
	@ExcelCell(value = "名称", index = 0)
	private String name;
	
	/** 代码 */
	@ExcelCell(value = "代码", index = 1)
	private String code;
	
	/** 顺序 */
	@ExcelCell(value = "顺序", index = 2)
	private Integer order;
	
	/** 时间 */
	@ExcelCell(value = "时间", index = 3)
	private Date datetime;
	
	/** 说明 */
	@ExcelCell(value = "说明", index = 4)
	private String desc;
	
	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 * @see #code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 读取顺序
	 * 
	 * @return
	 * @see #order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * 设置顺序
	 * 
	 * @param order
	 * @see #order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * 读取时间
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * 设置时间
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/**
	 * 读取说明
	 * 
	 * @return
	 * @see #desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置说明
	 * 
	 * @param desc
	 * @see #desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
