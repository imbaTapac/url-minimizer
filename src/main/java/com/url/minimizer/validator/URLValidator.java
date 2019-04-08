package com.url.minimizer.validator;

import static java.util.Objects.nonNull;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.url.minimizer.dto.URLCreateDTO;

@Component
public class URLValidator implements Validator {

	@Override
	public boolean supports (Class<?> aClass){
		return URLCreateDTO.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors){
		URLCreateDTO urlCreateDTO = (URLCreateDTO) o;
		UrlValidator urlValidator = UrlValidator.getInstance();

		if(!urlValidator.isValid(urlCreateDTO.getOriginalURL())){
			errors.rejectValue("originalURL","InvalidURL");
		}

		if(nonNull(urlCreateDTO.getExpiredDate()) && nonNull(urlCreateDTO.getActiveDays())){
			errors.rejectValue("expiredDate","EnterDateOrDays");
		}

	}
}
