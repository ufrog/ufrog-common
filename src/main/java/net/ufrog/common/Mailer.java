package net.ufrog.common;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.ufrog.common.app.App;
import net.ufrog.common.exception.ServiceException;
import net.ufrog.common.utils.Strings;

/**
 * 邮件寄送器
 * 
 * @author ultrafrog
 * @version 1.0, 2014-02-07
 * @since 1.0
 */
public class Mailer {

	private static final String PROP_SMTP_HOST		= "mail.smtp.host";
	private static final String PROP_SMTP_PORT		= "mail.smtp.port";
	private static final String PROP_SMTP_AUTH		= "mail.smtp.auth";
	private static final String PROP_SMTP_USERNAME	= "mail.smtp.username";
	private static final String PROP_SMTP_PASSWORD	= "mail.smtp.password";
	private static final String PROP_FROM			= "mail.from";
	
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	
	/** 消息 */
	private Message message;
	
	/** 构造函数 */
	public Mailer() {
		String host = App.config(PROP_SMTP_HOST);
		String port = App.config(PROP_SMTP_PORT);
		String username = App.config(PROP_SMTP_USERNAME);
		String password = App.config(PROP_SMTP_PASSWORD);
		String from = App.config(PROP_FROM);
		initialize(host, port, username, password, from);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param from
	 */
	public Mailer(String host, String port, String username, String password, String from) {
		initialize(host, port, username, password, from);
	}
	
	/**
	 * 添加收件地址
	 * 
	 * @param address
	 */
	public void addTo(String address) {
		try {
			message.addRecipient(RecipientType.TO, new InternetAddress(address));
		} catch (AddressException e) {
			throw new ServiceException("cannot add address '" + address + "'.", "exception.mail.to", e);
		} catch (MessagingException e) {
			throw new ServiceException("cannot add address '" + address + "'.", "exception.mail.to", e);
		}
	}
	
	/**
	 * 添加抄送地址
	 * 
	 * @param address
	 */
	public void addCc(String address) {
		try {
			message.addRecipient(RecipientType.CC, new InternetAddress(address));
		} catch (AddressException e) {
			throw new ServiceException("cannot add address '" + address + "'.", "exception.mail.cc", e);
		} catch (MessagingException e) {
			throw new ServiceException("cannot add address '" + address + "'.", "exception.mail.cc", e);
		}
	}
	
	/**
	 * 添加暗送地址
	 * 
	 * @param address
	 */
	public void addBcc(String address) {
		try {
			message.addRecipient(RecipientType.BCC, new InternetAddress(address));
		} catch (AddressException e) {
			throw new ServiceException("cannot add address '" + address + "'.", "exception.mail.bcc", e);
		} catch (MessagingException e) {
			throw new ServiceException("cannot add address '" + address + "'.", "exception.mail.bcc", e);
		}
	}
	
	/**
	 * 设置主题
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		try {
			message.setSubject(subject);
		} catch (MessagingException e) {
			throw new ServiceException("cannot set subject '" + subject + "'.", "exception.mail.subject", e);
		}
	}
	
	/**
	 * 设置纯文本内容
	 * 
	 * @param text
	 */
	public void setText(String text) {
		try {
			message.setText(text);
		} catch (MessagingException e) {
			throw new ServiceException("cannot set text '" + text + "'.", "exception.mail.content", e);
		}
	}
	
	/**
	 * 设置超文本内容
	 * 
	 * @param text
	 */
	public void setHtml(String text) {
		try {
			Multipart multipart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(text, "text/html; charset=utf-8");
			multipart.addBodyPart(html);
			message.setContent(multipart);
		} catch (MessagingException e) {
			throw new ServiceException("cannot set html '" + text + "'.", "exception.mail.content", e);
		}
	}
	
	/** 发送邮件 */
	public void send() {
		executorService.execute(new SendTask());
	}
	
	/**
	 * 初始化
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param from
	 */
	protected void initialize(String host, String port, String username, String password, String from) {
		// 初始化
		Properties props = new Properties();
		Authenticator authenticator = null;
		props.put(PROP_SMTP_HOST, host);
		props.put(PROP_SMTP_PORT, port);
		props.put(PROP_SMTP_AUTH, String.valueOf(!Strings.empty(username)));
		
		// 判断是否需要认证
		// 若用户名不为空则进行认证
		if (!Strings.empty(username)) authenticator = new Authenticator(username, password);
		
		// 定义消息
		message = new MimeMessage(Session.getDefaultInstance(props, authenticator));
		try {
			message.setFrom(new InternetAddress(from));
		} catch (AddressException e) {
			throw new ServiceException("cannot set address '" + from + "'.", "exception.mail.from", e);
		} catch (MessagingException e) {
			throw new ServiceException("cannot set address '" + from + "'.", "exception.mail.from", e);
		}
		
		// 打印日志
		Logger.debug("initialize mail host: %s, port: %s, username: %s, from: %s", host, port, username, from);
	}
	
	/**
	 * 发送纯文本邮件
	 * 
	 * @param subject
	 * @param text
	 * @param addresses
	 */
	public static void sendText(String subject, String text, String... addresses) {
		Mailer mailer = new Mailer();
		for (String addr: addresses) mailer.addTo(addr);
		mailer.setSubject(subject);
		mailer.setText(text);
		mailer.send();
	}
	
	/**
	 * 发送超文本邮件
	 * 
	 * @param subject
	 * @param html
	 * @param addresses
	 */
	public static void sendHtml(String subject, String html, String... addresses) {
		Mailer mailer = new Mailer();
		for (String addr: addresses) mailer.addTo(addr);
		mailer.setSubject(subject);
		mailer.setHtml(html);
		mailer.send();
	}
	
	/**
	 * 认证器
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2014-02-07
	 * @since 1.0
	 */
	protected class Authenticator extends javax.mail.Authenticator {
		
		/** 用户名 */
		private String username;
		
		/** 密码 */
		private String password;
		
		/**
		 * 构造函数
		 * 
		 * @param username
		 * @param password
		 */
		public Authenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}
		
		/* (non-Javadoc)
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}
	
	/**
	 * 发送任务
	 * 
	 * @author ultrafrog
	 * @version 1.0, 2014-03-13
	 * @since 1.0
	 */
	protected class SendTask implements Runnable {
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				message.setSentDate(new Date());
				Transport.send(message);
				if (Logger.isInfoEnabled()) Logger.info("send mail '%s' to '%s' success.", message.getSubject(), InternetAddress.toString(message.getAllRecipients()));
			} catch (MessagingException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		}
	}
}
