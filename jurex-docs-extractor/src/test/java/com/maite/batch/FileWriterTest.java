package com.maite.batch;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.maite.model.entities.Contrato;
import com.maite.model.services.IContratoService;

@RunWith(MockitoJUnitRunner.class)
public class FileWriterTest {
	
	@InjectMocks
	private FileWriter fileWriter;

	@Mock
	private IContratoService contratoService;
	
    @Test
    public void givenListContratos_whenFileWriterIsRetrieved_thenSaveContratoIsCalled() {
        
    	Optional<Contrato> contrato = Optional.of(new Contrato());
    	
    	List<Optional<Contrato>> contratoList = new ArrayList<>();
    	contratoList.add(contrato);
    	
    	List<List<Optional<Contrato>>> contratos = new ArrayList<>();
    	contratos.add(contratoList);
    	
    	try {
    		fileWriter.write(contratos);	
    	} catch (Exception e) {
			
		}
        
        verify(contratoService, times(1)).save(contrato.get());
    }
    
    @Test
    public void givenEmptyListContratos_whenFileWriterIsRetrieved_thenSaveContratoIsNotCalled() {
        
    	List<Optional<Contrato>> contratoList = new ArrayList<>();
    	
    	List<List<Optional<Contrato>>> contratos = new ArrayList<>();
    	contratos.add(contratoList);
    	
    	try {
    		fileWriter.write(contratos);	
    	} catch (Exception e) {
			
		}
        
        verify(contratoService, never()).save(ArgumentMatchers.any(Contrato.class));
    }

}
