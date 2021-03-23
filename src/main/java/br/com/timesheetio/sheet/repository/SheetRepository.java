package br.com.timesheetio.sheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.timesheetio.sheet.domain.Sheet;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Long>{

}
