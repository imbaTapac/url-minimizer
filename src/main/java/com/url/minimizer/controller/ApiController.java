package com.url.minimizer.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.minimizer.annotation.Logging;
import com.url.minimizer.dto.Action;
import com.url.minimizer.dto.ActionResultDTO;
import com.url.minimizer.dto.StatisticDTO;
import com.url.minimizer.dto.URLCreateDTO;
import com.url.minimizer.dto.URLUpdateDTO;
import com.url.minimizer.entity.URL;
import com.url.minimizer.logging.EventType;
import com.url.minimizer.mapper.DTOObjectURLMapper;
import com.url.minimizer.service.StatisticService;
import com.url.minimizer.service.URLMinimizerService;
import com.url.minimizer.validator.URLValidator;

@RestController
@RequestMapping(path = "/api/v1/minimizer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApiController {

	private final URLValidator urlValidator;
	private final DTOObjectURLMapper dtoObjectURLMapper;
	private final URLMinimizerService urlMinimizerService;
	private final StatisticService statisticService;

	@Autowired
	public ApiController(URLValidator urlValidator, DTOObjectURLMapper dtoObjectURLMapper, URLMinimizerService urlMinimizerService, StatisticService statisticService) {
		this.urlValidator = urlValidator;
		this.dtoObjectURLMapper = dtoObjectURLMapper;
		this.urlMinimizerService = urlMinimizerService;
		this.statisticService = statisticService;
	}

	@PostMapping(value = "/url/create")
	public ResponseEntity createURL(@ModelAttribute("url") URLCreateDTO urlDTO, BindingResult result) {
		urlValidator.validate(urlDTO, result);
		if(result.hasErrors()) {
			List<ObjectError> errorsList = result.getAllErrors();
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(errorsList);
		}

		URL url = dtoObjectURLMapper.toEntity(urlDTO);
		urlMinimizerService.minimizeURL(url);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(url.getMinimizedUrl());
	}

	@GetMapping(value = "/url/all")
	public ResponseEntity<Page<URL>> getAllURLs(Pageable pageable) {
		Page<URL> urls = urlMinimizerService.getAllURLs(pageable);
		return new ResponseEntity<>(urls, HttpStatus.OK);
	}

	@GetMapping(value = "/{minimizedURL}")
	@Logging(EventType.URL_REDIRECT)
	public ResponseEntity getURL(@PathVariable("minimizedURL") String url, HttpServletRequest request) {
		String originalURL = urlMinimizerService.restoreURL(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(originalURL));
		return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
	}

	@GetMapping(value = "/statistic/{minimizedURL}")
	public ResponseEntity getURLStatistic(@PathVariable("minimizedURL") String url){
		StatisticDTO statisticDTO = statisticService.getURLStatistic(url);
		return ResponseEntity.ok(statisticDTO);
	}

	@DeleteMapping(value = "/delete/{minimizedURL}")
	public ResponseEntity deleteURL(@PathVariable("minimizedURL") String url) {
		ActionResultDTO actionResultDTO = urlMinimizerService.deleteURL(url);
		return ResponseEntity.ok(actionResultDTO);
	}

	@PutMapping(value = "/update")
	public ResponseEntity updateURL(@RequestBody URLUpdateDTO updateDTO) {
		URL url = dtoObjectURLMapper.toEntity(updateDTO);
		URL result = urlMinimizerService.updateURL(url);
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "/{minimizedURL}/status/{action}")
	public ResponseEntity changeStatus(@PathVariable("minimizedURL") String url, @PathVariable Action action) {
		ActionResultDTO actionResultDTO = urlMinimizerService.changeActiveStatus(url, action);
		return ResponseEntity.ok(actionResultDTO);
	}
}
