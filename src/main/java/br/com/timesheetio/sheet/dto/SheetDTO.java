package br.com.timesheetio.sheet.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetDTO implements Serializable {
	
	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String title;

	private String parameterKey;

	private String userOwnerKey;

	private String userConsumerKey;
	
	private String sheetKey;

	private LocalDate createdDate;
	
	private LocalDate updatedDate;
}
