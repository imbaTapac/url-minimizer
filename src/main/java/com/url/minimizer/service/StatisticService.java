package com.url.minimizer.service;

import javax.servlet.http.HttpServletRequest;

import com.url.minimizer.dto.StatisticDTO;

public interface StatisticService {
	void addStatistic(String minimizedURL, HttpServletRequest request);
	StatisticDTO getURLStatistic(String minimizedURL);
}
