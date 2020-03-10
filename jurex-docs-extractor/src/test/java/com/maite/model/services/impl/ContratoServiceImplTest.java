package com.maite.model.services.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.maite.model.entities.Contrato;
import com.maite.model.repositories.IContratoRepository;

@RunWith(MockitoJUnitRunner.class)
public class ContratoServiceImplTest {

	@InjectMocks
	private ContratoServiceImpl contratoService;
	
    @Mock
    private IContratoRepository contratoRepository;
    
    @Test
    public void givenContrato_whenSaveContratoIsRetrieved_thenIsSaved() {
        
    	Contrato contrato = new Contrato();
    	
    	contratoService.save(contrato);
    	
        verify(contratoRepository, times(1)).save(contrato);
        
    }
    @Test
    public void whenFindAll_thenContratoRepositoryFindallIsExecuted() {


        contratoService.findAll();

        verify(contratoRepository, times(1)).findAll();

    }
   
}
