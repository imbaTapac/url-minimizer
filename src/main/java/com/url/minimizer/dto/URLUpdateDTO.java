package com.url.minimizer.dto;


import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class URLUpdateDTO implements Serializable {
	@JsonProperty("minimizedURL")
	private String minimizedURL;
	@JsonProperty("newURL")
	private String originalURL;
	@JsonProperty("expiredDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiredDate;
	@JsonProperty("activeDays")
	private Integer activeDays;
	@JsonProperty("isActive")
	private boolean active;

	public URLUpdateDTO (){
	}

	public String getMinimizedURL() {
		return minimizedURL;
	}

	public void setMinimizedURL(String minimizedURL) {
		this.minimizedURL = minimizedURL;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Integer getActiveDays() {
		return activeDays;
	}

	public void setActiveDays(Integer activeDays) {
		this.activeDays = activeDays;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "URLUpdateDTO{" +
				"minimizedURL='" + minimizedURL + '\'' +
				", originalURL='" + originalURL + '\'' +
				", expiredDate=" + expiredDate +
				", activeDays=" + activeDays +
				", active=" + active +
				'}';
	}
}
