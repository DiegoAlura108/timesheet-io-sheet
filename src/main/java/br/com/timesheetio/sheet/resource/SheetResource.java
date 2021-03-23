package br.com.timesheetio.sheet.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.timesheetio.sheet.configuration.SwaggerConfig;
import br.com.timesheetio.sheet.dto.ResponseDTO;
import br.com.timesheetio.sheet.dto.SheetDTO;
import br.com.timesheetio.sheet.service.SheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = SwaggerConfig.TAG_SHEET_OPERATION)
@RestController
@RequestMapping("/sheet")
public class SheetResource {

    private static final Logger logger = LoggerFactory.getLogger(SheetResource.class);
	
	@Autowired
	private SheetService sheetService;

	@ApiOperation(value = "This operation is used to find all sheets.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Sheets founded."),
			@ApiResponse(code = 404, message = "Sheets not found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@GetMapping
	public ResponseEntity<ResponseDTO<Page<SheetDTO>>> findAll(@RequestParam(name = "page", defaultValue = "0") int page, 
																@RequestParam(name = "limit", defaultValue = "12") int limit,
																@RequestParam(name = "direction", defaultValue = "asc") String direction,
																@RequestParam(name = "field", defaultValue = "id") String field){
		
		Direction directionSelected = direction.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(directionSelected, field));
		
		Page<SheetDTO> pageSheets = sheetService.findAll(pageable);
		
		ResponseDTO<Page<SheetDTO>> response = new ResponseDTO<>();
		response.setData(pageSheets);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).findAll(page, limit, direction, field)).withSelfRel());
		
		return ResponseEntity.ok(response);
	}
	

	@ApiOperation(value = "This operation is used to find sheets by id.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Sheets founded."),
			@ApiResponse(code = 404, message = "Sheets not found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<SheetDTO>> findById(@PathVariable("id") Long id){
		
		logger.info("FINDING SHEET OBJECT BY ID ...: {}", id);
		
		SheetDTO sheetFound = sheetService.findById(id);
		
		ResponseDTO<SheetDTO> response = new ResponseDTO<SheetDTO>();
		response.setData(sheetFound);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).findById(id)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).delete(id)).withRel("DELETE"));
		
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "This operation is used to save sheets.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Sheets saved."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@PostMapping
	public ResponseEntity<ResponseDTO<SheetDTO>> save(@RequestBody SheetDTO person) {
		
		logger.info("SAVING SHEET OBJECT ...: {}", person);
		
		SheetDTO sheetFound = sheetService.save(person);
		
		ResponseDTO<SheetDTO> response = new ResponseDTO<SheetDTO>();
		response.setData(sheetFound);
		response.setStatus(HttpStatus.CREATED.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).findById(sheetFound.getId())).withRel("GET"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).update(sheetFound)).withRel("UPDATE"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).delete(sheetFound.getId())).withRel("DELETE"));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
	@ApiOperation(value = "This operation is used to update sheets.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Sheets updated."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@PutMapping
	public ResponseEntity<ResponseDTO<SheetDTO>> update(@RequestBody SheetDTO person) {
		
		logger.info("UPDATING SHEET OBJECT ...: {}", person);
		
		SheetDTO sheetUpdated = sheetService.update(person);
		
		ResponseDTO<SheetDTO> response = new ResponseDTO<SheetDTO>();
		response.setData(sheetUpdated);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).findById(sheetUpdated.getId())).withRel("GET"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).update(sheetUpdated)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetResource.class).delete(sheetUpdated.getId())).withRel("DELETE"));		
		
		return ResponseEntity.ok(response);
	}

	
	@ApiOperation(value = "This operation is used to delete sheets.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 204, message = "Sheet deleted."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "Sheet Not Found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){

		logger.info("DELETING SHEET OBJECT ...: {}", id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
