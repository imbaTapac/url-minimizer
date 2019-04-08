package com.url.minimizer.dto;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DateRedirectDTO implements Serializable {
	@JsonProperty("date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "Europe/Kiev")
	private Date date;
	@JsonProperty("count_of_redirect")
	private Integer redirectCount;

	public DateRedirectDTO() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getRedirectCount() {
		return redirectCount;
	}

	public void setRedirectCount(Integer redirectCount) {
		this.redirectCount = redirectCount;
	}

	@Override
	public String toString() {
		return "DateRedirectDTO{" +
				"date=" + date +
				", redirectCount=" + redirectCount +
				'}';
	}
}
