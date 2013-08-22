package net.ufrog.common.dictionary;

import net.ufrog.common.dictionary.annotation.Element;

/**
 * 布尔
 * 
 * @author ultrafrog
 * @version 1.0, 2012-10-18
 * @since 1.0
 */
public enum Bool {

	@Element("否")
	FALSE("00"),
	
	@Element("是")
	TRUE("10");
	
	/** 值 */
	private String value;
	
	/**
	 * @param value
	 */
	private Bool(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return value;
	}
}
