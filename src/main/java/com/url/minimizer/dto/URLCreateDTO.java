package com.url.minimizer.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class URLCreateDTO implements Serializable {
	@JsonProperty("originalURL")
	private String originalURL;
	@JsonProperty("expiredDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiredDate;
	@JsonProperty("activeDays")
	private Integer activeDays;
	@JsonProperty("isActive")
	private boolean isActive;

	public URLCreateDTO() {
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
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	@Override
	public String toString() {
		return "URLCreateDTO{" +
				"originalURL='" + originalURL + '\'' +
				", expiredDate=" + expiredDate +
				", activeDays=" + activeDays +
				", isActive=" + isActive +
				'}';
	}
}
