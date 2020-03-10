package com.maite.jurex.model.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.maite.jurex.model.entities.TMPMaite;
import com.maite.jurex.model.services.ITMPMaiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maite.jurex.model.repositories.ITMPMaiteRepository;

@Service
@Transactional("jurexTransactionManager")
public class TMPMaiteServiceImpl implements ITMPMaiteService {

	@Autowired
	private ITMPMaiteRepository tmpMaiteRepository;
	
	public Optional<TMPMaite> findByAsunto(String asunto) {
		return Optional.ofNullable(tmpMaiteRepository.findByAsunto(asunto));
	}
	
	public List<TMPMaite> findAll(int page, int pageSize) {
		List<TMPMaite> listResul = new ArrayList<>();
		
		Pageable paging = PageRequest.of(page, pageSize);
		Page<TMPMaite> pageable = tmpMaiteRepository.findAll(paging);
		if (pageable.hasContent()) {
			listResul = pageable.getContent();
		}
		return listResul.stream().collect(Collectors.toList());
	}
	
}
