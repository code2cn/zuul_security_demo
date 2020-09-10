package com.nic.zuul.security.bean;

public class RespBean {
	private static RespBean instance;
	public static RespBean getInstance()
	{
		if(instance==null)
		{
			instance=new RespBean();
		}
		return instance;
	}
	private String msg;
	private int code;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
