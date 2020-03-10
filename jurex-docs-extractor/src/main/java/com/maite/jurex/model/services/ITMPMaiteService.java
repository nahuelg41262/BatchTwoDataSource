package com.maite.jurex.model.services;

import java.util.List;
import java.util.Optional;

import com.maite.jurex.model.entities.TMPMaite;

public interface ITMPMaiteService {
	
	public Optional<TMPMaite> findByAsunto(String asunto);
	
	List<TMPMaite> findAll(int page, int pageSize);
	
}
