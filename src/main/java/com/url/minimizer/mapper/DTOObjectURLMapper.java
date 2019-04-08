package com.url.minimizer.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.url.minimizer.dto.URLCreateDTO;
import com.url.minimizer.dto.URLUpdateDTO;
import com.url.minimizer.entity.URL;
import com.url.minimizer.exception.URLMinimizerBaseException;

@Component
public class DTOObjectURLMapper implements AbstractDTOMapper<URL, Object> {

	private final ModelMapper modelMapper;

	@Autowired
	public DTOObjectURLMapper(ModelMapper modelMapper){
		this.modelMapper = modelMapper;
	}

	@Override
	public URL toEntity(Object dto) {
		if(dto instanceof URLCreateDTO || dto instanceof URLUpdateDTO) {
			return modelMapper.map(dto, URL.class);
		}
		else {
			throw new URLMinimizerBaseException(400,"URL Model parse exception");
		}
	}
}
