package com.url.minimizer.dto;

import java.io.Serializable;

public class ActionResultDTO implements Serializable {
	private Action action;
	private String message;

	public ActionResultDTO(){
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
