package com.url.minimizer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "url")
@JsonIgnoreProperties("statistics")
public class URL implements Serializable {
	@Id
	@Column(name = "id_url", columnDefinition = "BIGINT(20)")
	private Long id;

	@Column(columnDefinition = "NVARCHAR(2000)", name = "original_url")
	private String originalUrl;

	@Column(columnDefinition = "NVARCHAR(26)", name = "minimized_url")
	private String minimizedUrl;

	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "date_expired")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;

	@Column(name = "active_days")
	private Integer activeDays;

	@Column(name = "isActive")
	private boolean active;

	@OneToMany(mappedBy = "url", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Statistic> statistics = new ArrayList<>();

	public URL() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getMinimizedUrl() {
		return minimizedUrl;
	}

	public void setMinimizedUrl(String minimizedUrl) {
		this.minimizedUrl = minimizedUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}

	@Override
	public String toString() {
		return "URL{" +
				"id=" + id +
				", originalUrl='" + originalUrl + '\'' +
				", minimizedUrl='" + minimizedUrl + '\'' +
				", createdDate=" + createdDate +
				", expiredDate=" + expiredDate +
				", activeDays=" + activeDays +
				", active=" + active +
				'}';
	}
}
