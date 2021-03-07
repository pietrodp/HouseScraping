package it.pdp.webscraper.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.pdp.webscraper.entity.Agenzia;

public interface AgenziaRepository extends CrudRepository<Agenzia, Long>{
	
	List<Agenzia> findByNome(String nome);
	Agenzia findById(long id);

}
