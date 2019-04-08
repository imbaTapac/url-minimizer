package com.url.minimizer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "statistic")
public class Statistic implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private Long id;

	@Column(name = "ip_address",columnDefinition = "NVARCHAR(15)")
	@Pattern(message = "wrong IP",regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
	private String ipAddress;

	@Column(name = "browser",columnDefinition = "NVARCHAR(128)")
	private String browserName;

	@Column(name = "record_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_url")
	private URL url;

	public Statistic(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Statistic{" +
				"id=" + id +
				", ipAddress='" + ipAddress + '\'' +
				", browserName='" + browserName + '\'' +
				", recordDate=" + recordDate +
				", url=" + url +
				'}';
	}
}
