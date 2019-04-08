package com.url.minimizer.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticDTO implements Serializable {

	@JsonProperty("url")
	private String minimizedURL;

	@JsonProperty("count_of_redirect")
	private Integer redirectCount = 0;

	@JsonProperty("chrome_redirect_count")
	private Integer chromeRedirectCount = 0;

	@JsonProperty("IE_redirect_count")
	private Integer ieRedirectCount = 0;

	@JsonProperty("mozilla_redirect_count")
	private Integer mozillaRedirectCount = 0;

	@JsonProperty("opera_redirect_count")
	private Integer operaRedirectCount = 0;

	@JsonProperty("microsoft_edge_redirect_count")
	private Integer edgeRedirectCount = 0;

	@JsonProperty("redirect_by_date")
	private List<DateRedirectDTO> dateRedirectDTOS = new ArrayList<>();

	public StatisticDTO() {
	}

	public String getMinimizedURL() {
		return minimizedURL;
	}

	public void setMinimizedURL(String minimizedURL) {
		this.minimizedURL = minimizedURL;
	}

	public Integer getRedirectCount() {
		return redirectCount;
	}

	public void setRedirectCount(Integer redirectCount) {
		this.redirectCount = redirectCount;
	}

	public Integer getChromeRedirectCount() {
		return chromeRedirectCount;
	}

	public void setChromeRedirectCount(Integer chromeRedirectCount) {
		this.chromeRedirectCount = chromeRedirectCount;
	}

	public Integer getIeRedirectCount() {
		return ieRedirectCount;
	}

	public void setIeRedirectCount(Integer ieRedirectCount) {
		this.ieRedirectCount = ieRedirectCount;
	}

	public Integer getMozillaRedirectCount() {
		return mozillaRedirectCount;
	}

	public void setMozillaRedirectCount(Integer mozillaRedirectCount) {
		this.mozillaRedirectCount = mozillaRedirectCount;
	}

	public Integer getOperaRedirectCount() {
		return operaRedirectCount;
	}

	public void setOperaRedirectCount(Integer operaRedirectCount) {
		this.operaRedirectCount = operaRedirectCount;
	}

	public Integer getEdgeRedirectCount() {
		return edgeRedirectCount;
	}

	public void setEdgeRedirectCount(Integer edgeRedirectCount) {
		this.edgeRedirectCount = edgeRedirectCount;
	}

	public List<DateRedirectDTO> getDateRedirectDTOS() {
		return dateRedirectDTOS;
	}

	public void setDateRedirectDTOS(List<DateRedirectDTO> dateRedirectDTOS) {
		this.dateRedirectDTOS = dateRedirectDTOS;
	}

}
