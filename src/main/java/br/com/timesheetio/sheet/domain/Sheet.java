package br.com.timesheetio.sheet.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table
@Entity(name = "SHEET")
public class Sheet implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "TITLE")
	private String title;

	@Column(name = "PARAMETER_ID")
	private String parameterKey;

	@Column(name = "USER_OWNER_KEY")
	private String userOwnerKey;

	@Column(name = "USER_CONSUMER_KEY")
	private String userConsumerKey;
	
	@Column(name = "SHEET_KEY")
	private String sheetKey;

	@Column(name= "CREATED_DATE")
	private LocalDate createdDate;
	
	@Column(name= "UPDATED_DATE")
	private LocalDate updatedDate;
	
	@PrePersist
	private void creationDate() {
		
		this.createdDate = LocalDate.now();
	}
	
	@PreUpdate
	private void updationDate() {
		
		this.updatedDate = LocalDate.now();
	}
}
