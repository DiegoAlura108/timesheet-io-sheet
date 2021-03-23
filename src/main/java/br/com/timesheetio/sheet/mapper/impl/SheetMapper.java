package br.com.timesheetio.sheet.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.timesheetio.sheet.domain.Sheet;
import br.com.timesheetio.sheet.dto.SheetDTO;
import br.com.timesheetio.sheet.mapper.IMapper;

@Component
@Qualifier("sheetMapper")
public class SheetMapper implements IMapper<Sheet, SheetDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public SheetDTO convertEntityToDto(Sheet e) {

		return modelMapper.map(e, SheetDTO.class);
	}

	@Override
	public Sheet convertDtoToEntity(SheetDTO d) {

		return modelMapper.map(d, Sheet.class);
	}
}
