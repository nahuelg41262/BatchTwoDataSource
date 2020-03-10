package com.maite.jurex.model.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.maite.jurex.model.entities.TMPMaite;
import com.maite.jurex.model.repositories.ITMPMaiteRepository;

@RunWith(MockitoJUnitRunner.class)
public class TMPMaiteServiceImplTest {

	@InjectMocks
	private TMPMaiteServiceImpl tmpMaiteService;
	
    @Mock
    private ITMPMaiteRepository tmpMaiteRepository;
    
    @Test
    public void givenPageAndSize_whenFindTMPMaiteRecords_thenListIsReceived() {
        
    	TMPMaite tmpMaite = new TMPMaite();
    	List<TMPMaite> tmpMaiteList = new ArrayList<>();
    	tmpMaiteList.add(tmpMaite);
    	Page<TMPMaite> pageable = new PageImpl<>(tmpMaiteList);
    	int page = 0;
    	int pageSize = 2;
    	
    	Pageable paging = PageRequest.of(page, pageSize);
    	when(tmpMaiteRepository.findAll(paging)).thenReturn(pageable);
    	
    	List<TMPMaite> listResul = tmpMaiteService.findAll(page, pageSize);
    	
    	assertThat(listResul.size()).isEqualTo(1);
        
    }
    
    @Test
    public void givenPageAndSize_whenFindTMPMaiteRecords_thenEmptyListIsReceived() {
        
    	List<TMPMaite> tmpMaiteList = new ArrayList<>();
    	Page<TMPMaite> pageable = new PageImpl<>(tmpMaiteList);
    	int page = 0;
    	int pageSize = 2;
    	
    	Pageable paging = PageRequest.of(page, pageSize);
    	when(tmpMaiteRepository.findAll(paging)).thenReturn(pageable);
    	
    	List<TMPMaite> listResul = tmpMaiteService.findAll(page, pageSize);
    	
    	assertThat(listResul.size()).isEqualTo(0);
        
    }
    
}
