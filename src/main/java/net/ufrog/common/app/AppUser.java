package net.ufrog.common.app;

import java.io.Serializable;

/**
 * 应用用户
 *
 * @author ultrafrog
 * @version 1.0, 2013-10-1
 * @since 1.0
 */
public class AppUser implements Serializable {

	private static final long serialVersionUID = 2841689479876685149L;

	/** 代码 */
	private String id;
	
	/** 帐号 */
	private String account;
	
	/** 名称 */
	private String name;

	/**
	 * 读取代码
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置代码
	 * 
	 * @param id
	 * @see #id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 读取帐号
	 * 
	 * @return
	 * @see #account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置帐号
	 * 
	 * @param account
	 * @see #account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

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
}
