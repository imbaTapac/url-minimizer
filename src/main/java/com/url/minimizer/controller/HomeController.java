package com.url.minimizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.url.minimizer.dto.URLCreateDTO;
import com.url.minimizer.entity.URL;
import com.url.minimizer.mapper.DTOObjectURLMapper;
import com.url.minimizer.service.URLMinimizerService;
import com.url.minimizer.validator.URLValidator;

@Controller
public class HomeController {

	private final URLValidator urlValidator;
	private final DTOObjectURLMapper dtoObjectURLMapper;
	private final URLMinimizerService urlMinimizerService;

	@Autowired
	public HomeController(URLValidator urlValidator, DTOObjectURLMapper dtoObjectURLMapper, URLMinimizerService urlMinimizerService) {
		this.urlValidator = urlValidator;
		this.dtoObjectURLMapper = dtoObjectURLMapper;
		this.urlMinimizerService = urlMinimizerService;
	}

	@GetMapping(value = {"/", "/minimizer"})
	public String main(Model model) {
		model.addAttribute("url", new URLCreateDTO());
		return "minimizer";
	}

	@PostMapping(value = "/createURL", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody
	String createURL(@ModelAttribute("url") URLCreateDTO urlDTO, BindingResult result) {
		urlValidator.validate(urlDTO, result);
		if(result.hasErrors()) {
			return "minimizer";
		}

		URL url = dtoObjectURLMapper.toEntity(urlDTO);
		return urlMinimizerService.minimizeURL(url);
	}
}
