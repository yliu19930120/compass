package com.core.exception;
/**
 * 无翻页字段异常
 * @author liuyo
 *
 */
public class NoTagException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -320815219872386001L;

	public NoTagException(String msg){
		
		super(msg);
	}
}
