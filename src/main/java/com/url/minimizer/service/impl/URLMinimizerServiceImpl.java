package com.url.minimizer.service.impl;

import static com.url.minimizer.constants.Constants.BASE;
import static com.url.minimizer.constants.Constants.CHARACTERS;
import static com.url.minimizer.constants.Constants.URL_START;
import static com.url.minimizer.utils.DateUtils.isDateExpired;
import static com.url.minimizer.utils.DateUtils.isDaysExpired;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang.StringUtils.reverse;

import java.util.Optional;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.url.minimizer.dto.Action;
import com.url.minimizer.dto.ActionResultDTO;
import com.url.minimizer.entity.URL;
import com.url.minimizer.exception.URLMinimizerBaseException;
import com.url.minimizer.repository.URLRepository;
import com.url.minimizer.service.URLMinimizerService;

@Service
public class URLMinimizerServiceImpl implements URLMinimizerService {

	private static final Logger LOG = LoggerFactory.getLogger(URLMinimizerServiceImpl.class);

	@Value("${url.minimize.start-point}")
	private Long startPoint;

	private final URLRepository urlRepository;

	@Autowired
	public URLMinimizerServiceImpl(URLRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	@Override
	public String minimizeURL(URL url) {
		minimize(url);
		url.setCreatedDate(new DateTime().toDate());
		LOG.debug("Trying to save URL [{}]", url);
		long urlId = idChecker();
		url.setId(urlId);
		URL resultURL = urlRepository.save(url);
		LOG.debug("URL was successfully saved.\n Result URL is [{}]", resultURL);
		return URL_START + resultURL.getMinimizedUrl();
	}

	private void minimize(URL urL) {
		Optional<URL> url = urlRepository.findTopByOrderByIdDesc();
		long startIndex = startPoint;
		if(url.isPresent()) {
			startIndex = url.get().getId() + 1;
		}
		StringBuilder sb = new StringBuilder();
		while(startIndex > 0) {
			sb.append(CHARACTERS[Math.toIntExact(startIndex % BASE)]);
			startIndex /= BASE;
		}
		String minimizedURL = sb.reverse().toString();
		LOG.debug("Minimized URL is {}", minimizedURL);
		urL.setMinimizedUrl(minimizedURL);
	}

	@Override
	public String restoreURL(String minimizedURL) {
		String originMinimizedURL = minimizedURL;
		minimizedURL = reverse(minimizedURL);
		long idURL = 0;
		char[] urlToChars = minimizedURL.toCharArray();
		for(int i = 0; i < urlToChars.length; i++) {
			int charIndex = ArrayUtils.indexOf(CHARACTERS, urlToChars[i]);
			idURL += charIndex * Math.pow(BASE, i);
		}
		LOG.debug("URL {} id is {}", originMinimizedURL, idURL);
		Optional<URL> url = Optional.of(urlRepository.findById(idURL).orElseThrow(() -> new URLMinimizerBaseException(404,"Not Found")));
		URL result = url.get();
		if(result.isActive()) {
			if( (nonNull(result.getExpiredDate()) && isDateExpired(result.getExpiredDate())         )
					|| (nonNull(result.getActiveDays()) && isDaysExpired(result.getCreatedDate(),result.getActiveDays()) ) ) {
				throw new URLMinimizerBaseException(404, String.format("This url %s%s is expired", URL_START, result.getMinimizedUrl()));
			}
			else {
				return result.getOriginalUrl();
			}
		} else {
			throw new URLMinimizerBaseException(404, String.format("This url %s%s is wrong or not active", URL_START, result.getMinimizedUrl()));
		}
	}

	@Override
	public ActionResultDTO deleteURL(String minimizedURL) {
		ActionResultDTO resultDTO = new ActionResultDTO();
		Optional<URL> url = Optional.of(urlRepository.findByMinimizedUrl(minimizedURL).orElseThrow(() -> new URLMinimizerBaseException(404, "Incorrect URL")));
		urlRepository.delete(url.get());
		resultDTO.setMessage(String.format("%s%s was successfully deleted from DB", URL_START, minimizedURL));
		resultDTO.setAction(Action.DELETE);
		return resultDTO;
	}

	@Override
	public Page<URL> getAllURLs(Pageable pageable) {
		return urlRepository.findAll(pageable);
	}

	@Override
	public URL updateURL(URL url) {
		Optional<URL> toReturn = Optional.of(urlRepository.findByMinimizedUrl(url.getMinimizedUrl()).orElseThrow(() -> new URLMinimizerBaseException(400, "Wrong URL")));
		url.setId(toReturn.get().getId());
		return urlRepository.save(url);
	}

	@Override
	public ActionResultDTO changeActiveStatus(String url, Action action) {
		ActionResultDTO resultDTO = new ActionResultDTO();
		Optional<URL> toReturn = Optional.of(urlRepository.findByMinimizedUrl(url).orElseThrow(() -> new URLMinimizerBaseException(400, "Wrong URL")));
		URL result = toReturn.get();
		if(action.equals(Action.ACTIVATE) && result.isActive()) {
			resultDTO.setMessage(String.format("This URL [%s] is already active", url));
		} else if(action.equals(Action.DEACTIVATE) && !result.isActive()) {
			resultDTO.setMessage(String.format("This URL [%s] is already not active", url));
		} else {
			boolean isActive = !action.equals(Action.DEACTIVATE);
			result.setActive(isActive);
			urlRepository.save(result);
			String actionResult = isActive ? "activated" : "deactivated";
			resultDTO.setMessage(String.format("URL [%s] is successfully [%s]", url, actionResult));
		}
		resultDTO.setAction(action);
		return resultDTO;
	}

	private long idChecker() {
		URL url = urlRepository.findTopByOrderByIdDesc().orElse(null);
		if(isNull(url)) {
			return startPoint;
		}
		return url.getId() + 1;
	}
}
