package br.com.timesheetio.sheet.mapper;

public interface IMapper <E, D>{

	public D convertEntityToDto(E e);
	
	public E convertDtoToEntity(D d);
}
