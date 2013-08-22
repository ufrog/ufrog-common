package net.ufrog.common;

import java.io.Serializable;

/**
 * 上下文用户
 *
 * @author ultrafrog
 * @version 1.0, 2013-8-11
 * @since 1.0
 */
public class ContextUser implements Serializable {

	private static final long serialVersionUID = -6633391647941263017L;

	/** 编号 */
	private String id;
	
	/** 帐号 */
	private String account;
	
	/** 名称 */
	private String name;

	/**
	 * 构造函数
	 */
	public ContextUser() {}
	
	/**
	 * 构造函数
	 * 
	 * @param id
	 */
	public ContextUser(String id) {
		this(id, null);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param id
	 * @param account
	 */
	public ContextUser(String id, String account) {
		this(id, account, null);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param id
	 * @param account
	 * @param name
	 */
	public ContextUser(String id, String account, String name) {
		this.id = id;
		this.account = account;
		this.name = name;
	}
	
	/**
	 * 读取编号
	 * 
	 * @return
	 * @see #id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置编号
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
