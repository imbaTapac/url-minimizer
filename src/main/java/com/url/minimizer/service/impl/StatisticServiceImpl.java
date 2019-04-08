package com.url.minimizer.service.impl;

import static com.url.minimizer.constants.Constants.LOCALHOST;
import static com.url.minimizer.constants.Constants.URL_START;
import static com.url.minimizer.dto.BrowserType.CHROME;
import static com.url.minimizer.dto.BrowserType.EDGE;
import static com.url.minimizer.dto.BrowserType.FIREFOX;
import static com.url.minimizer.dto.BrowserType.IE;
import static com.url.minimizer.dto.BrowserType.OPERA;
import static java.util.Objects.isNull;

import java.net.Inet4Address;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.minimizer.dto.DateRedirectDTO;
import com.url.minimizer.dto.StatisticDTO;
import com.url.minimizer.entity.Statistic;
import com.url.minimizer.entity.URL;
import com.url.minimizer.exception.URLMinimizerBaseException;
import com.url.minimizer.repository.StatisticRepository;
import com.url.minimizer.repository.URLRepository;
import com.url.minimizer.service.StatisticService;

import eu.bitwalker.useragentutils.UserAgent;

@Service
public class StatisticServiceImpl implements StatisticService {

	private static final Logger LOG = LoggerFactory.getLogger(StatisticServiceImpl.class);

	private final URLRepository urlRepository;
	private final StatisticRepository statisticRepository;

	@Autowired
	public StatisticServiceImpl(URLRepository urlRepository, StatisticRepository statisticRepository) {
		this.urlRepository = urlRepository;
		this.statisticRepository = statisticRepository;
	}

	@Override
	public void addStatistic(String minimizedURL, HttpServletRequest request) {
		Optional<URL> url = Optional.of(urlRepository.findByMinimizedUrl(minimizedURL).
				orElseThrow(() -> new URLMinimizerBaseException(400, "Wrong URL")));
		Statistic statistic = new Statistic();
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-agent"));
		LOG.debug("Browser name is {}", userAgent.getBrowser().getName());
		LOG.debug("Browser version is {}", userAgent.getBrowserVersion());
		String ipAddress = request.getRemoteAddr();
		String ip = Inet4Address.getLoopbackAddress().getHostAddress();
		LOG.debug("IP address is {}", ipAddress);
		LOG.debug("Inet4Address host is {}", ip);
		String resultIP = ip.equals(LOCALHOST) ? ip : ipAddress;
		statistic.setBrowserName(userAgent.getBrowser().getName());
		statistic.setIpAddress(resultIP);
		statistic.setRecordDate(new DateTime().toDate());
		statistic.setUrl(url.get());
		LOG.debug("Result statistic is [{}]", statistic);
		statisticRepository.save(statistic);
	}

	@Override
	public StatisticDTO getURLStatistic(String minimizedURL) {
		URL url = urlRepository.findByMinimizedUrl(minimizedURL).orElseThrow(() -> new URLMinimizerBaseException(404, "Wrong URL"));
		List<Statistic> statistics = statisticRepository.findAllByUrlId(url.getId());
		if(isNull(statistics) || statistics.isEmpty()) {
			throw new URLMinimizerBaseException(404,String.format("There is no statistic for [%s] url",URL_START + minimizedURL));
		}
		StatisticDTO statisticDTO = new StatisticDTO();
		statisticDTO.setMinimizedURL(URL_START + minimizedURL);

		statisticDTO.setRedirectCount(statistics.size());
		for(Statistic statistic : statistics) {
			browserRedirectCounter(statistic, statisticDTO);
		}
		dateRedirectCounter(statistics, statisticDTO);
		return statisticDTO;
	}

	private void browserRedirectCounter(Statistic statistic, StatisticDTO statisticDTO) {
		if(CHROME.getType().equals(statistic.getBrowserName())) {
			statisticDTO.setChromeRedirectCount(statisticDTO.getChromeRedirectCount() + 1);
		}
		if(FIREFOX.getType().equals(statistic.getBrowserName())) {
			statisticDTO.setMozillaRedirectCount(statisticDTO.getMozillaRedirectCount() + 1);
		}
		if(OPERA.getType().equals(statistic.getBrowserName())) {
			statisticDTO.setOperaRedirectCount(statisticDTO.getOperaRedirectCount() + 1);
		}
		if(IE.getType().equals(statistic.getBrowserName())) {
			statisticDTO.setIeRedirectCount(statisticDTO.getIeRedirectCount() + 1);
		}
		if(EDGE.getType().equals(statistic.getBrowserName())) {
			statisticDTO.setEdgeRedirectCount(statisticDTO.getEdgeRedirectCount() + 1);
		}
	}

	private void dateRedirectCounter(List<Statistic> statistics, StatisticDTO statisticDTO) {
		List<LocalDate> uniqueDates = statistics.stream()
				.map(s -> LocalDate.fromDateFields(s.getRecordDate()))
				.distinct().collect(Collectors.toList());
		for(LocalDate localDate : uniqueDates) {
			DateRedirectDTO dateRedirectDTO = new DateRedirectDTO();
			dateRedirectDTO.setDate(localDate.toDate());
			int redirectCount = (int) statistics.stream()
					.filter(s -> LocalDate.fromDateFields(s.getRecordDate()).equals(localDate))
					.count();
			dateRedirectDTO.setRedirectCount(redirectCount);
			statisticDTO.getDateRedirectDTOS().add(dateRedirectDTO);
		}
	}
}
