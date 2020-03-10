package com.maite.jurex.model.repositories;

import com.maite.jurex.model.entities.TMPMaite;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITMPMaiteRepository  extends PagingAndSortingRepository<TMPMaite, Long> {

	public TMPMaite findByAsunto(String asunto);
	
	
}
