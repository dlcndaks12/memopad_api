package com.almond.common.domain;

import java.io.Serializable;

public class CommonResponse implements Serializable {
	
	private static final long serialVersionUID = -4437304850925467729L;

	private String result;
	private String message;
	private Object data;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
