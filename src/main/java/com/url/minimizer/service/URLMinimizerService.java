package com.url.minimizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.url.minimizer.dto.Action;
import com.url.minimizer.dto.ActionResultDTO;
import com.url.minimizer.entity.URL;

public interface URLMinimizerService {
	String minimizeURL(URL url);

	String restoreURL(String url);

	ActionResultDTO deleteURL(String url);

	URL updateURL(URL url);

	Page<URL> getAllURLs(Pageable pageable);

	ActionResultDTO changeActiveStatus(String url, Action action);
}
