package com.maite.batch;

import com.maite.batch.FileProcessor;
import com.maite.batch.services.converter.IConverterService;
import com.maite.batch.services.converter.impl.ConverterImpl;
import com.maite.batch.services.converter.strategy.ContextStrategyConverter;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FileProcessorTest {
    @InjectMocks
    FileProcessor fileProcessor;
    @Mock
    ConverterImpl converterService;

    @Test
    public void givenListTMPMaite_whenProcess_thenListIsConvertAndContratosIsRetrived() throws Exception {
        //given
        Blob blob = new SerialBlob("blob.test".getBytes());

        TMPMaite tmpMaite1 = new TMPMaite();
        tmpMaite1.setCaratula("tmpMaite1.test");
        tmpMaite1.setArchivo(blob);

        TMPMaite tmpMaite2 = new TMPMaite();
        tmpMaite2.setCaratula("tmpMaite2.test");
        tmpMaite2.setArchivo(blob);

        Contrato contrato1 = new Contrato();
        contrato1.setPathDelArchivo("path1.test");
        Contrato contrato2 = new Contrato();
        contrato2.setPathDelArchivo("path2.test");

        List<TMPMaite> tmpMaiteList = List.of(tmpMaite1, tmpMaite2);
        //when
        when(converterService.convert(tmpMaite1)).thenReturn(Optional.of(contrato1));
        when(converterService.convert(tmpMaite2)).thenReturn(Optional.of(contrato2));
        List<Optional<Contrato>> contratos = fileProcessor.process(tmpMaiteList);

        //then
        assertThat(contratos.size()).isEqualTo(2);
        ArgumentCaptor<TMPMaite> argument = ArgumentCaptor.forClass(TMPMaite.class);
        verify(converterService, times(2)).convert(argument.capture());
        assertThat(contratos.get(0).get().getPathDelArchivo()).isEqualTo("path1.test");

    }
}
