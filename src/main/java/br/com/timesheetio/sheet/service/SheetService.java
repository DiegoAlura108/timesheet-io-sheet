package br.com.timesheetio.sheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.timesheetio.sheet.domain.Sheet;
import br.com.timesheetio.sheet.dto.SheetDTO;
import br.com.timesheetio.sheet.exception.ObjectAlreadyExistsException;
import br.com.timesheetio.sheet.exception.ObjectNotFoundException;
import br.com.timesheetio.sheet.mapper.impl.SheetMapper;
import br.com.timesheetio.sheet.repository.SheetRepository;

@Service
public class SheetService {

	private static final String MESSAGE_OBJECT_ALREADY_EXIST = "This Sheet Object Already Exists.";

	private static final String MESSAGE_OBJECT_NOT_FOUND = "This Sheet Object Not Found.";
	
	@Autowired
	private SheetMapper sheetMapper;
	
	@Autowired
	private SheetRepository sheetRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	public Page<SheetDTO> findAll(Pageable pageable){
		
		return sheetRepository.findAll(pageable).map(sheetMapper::convertEntityToDto);
	}
	
	public SheetDTO findById(Long id) {
		
		Sheet sheetEntity = sheetRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		
		return sheetMapper.convertEntityToDto(sheetEntity);
	}
	
	public SheetDTO save(SheetDTO sheetDTO) {
		
		if(sheetDTO.getId() != null) {
			
			throw new ObjectAlreadyExistsException(MESSAGE_OBJECT_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		
		sheetDTO.setSheetKey(bCryptPasswordEncoder.encode(sheetDTO.getTitle()));
		
		Sheet sheetEntity = sheetRepository.save(sheetMapper.convertDtoToEntity(sheetDTO));
		
		return sheetMapper.convertEntityToDto(sheetEntity);
	}
	

	
	public SheetDTO update(SheetDTO sheetDTO) {
		
		if(sheetDTO.getId() == null) {
			
			throw new ObjectNotFoundException(MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		
		Sheet sheetEntity = sheetRepository.save(sheetMapper.convertDtoToEntity(sheetDTO));
		
		return sheetMapper.convertEntityToDto(sheetEntity);
	}
	
	public void delete(Long id) {
		
		SheetDTO sheet = this.findById(id);
		
		sheetRepository.delete(sheetMapper.convertDtoToEntity(sheet));
	}
}
