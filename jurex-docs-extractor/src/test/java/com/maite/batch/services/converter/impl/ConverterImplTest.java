package com.maite.batch.services.converter.impl;

import com.maite.batch.services.converter.strategy.ContextStrategyConverter;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ConverterImplTest {

    @InjectMocks
    ConverterImpl converterService;

    @Mock
    ContextStrategyConverter contextStrategyConverter;

    @Test
    public void givenTMPMaite_WhenConvert_ThenOptionalOfContratoIsReceivedAndContexStrategyConvertIsExecuted() {

        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setCaratula("tmpMaite.test");
        Contrato contratoTest = new Contrato();
        contratoTest.setPathDelArchivo("contrato.test");

        when(contextStrategyConverter.convertRegistry(tmpMaite)).thenReturn(Optional.of(contratoTest));
        Optional<Contrato> contrato = converterService.convert(tmpMaite);

        verify(contextStrategyConverter,times(1)).convertRegistry(tmpMaite);
        assertThat(contrato.get().getPathDelArchivo()).isEqualTo(contratoTest.getPathDelArchivo());
    }
    @Test
    public void givenTMPMaite_WhenConvertFail_ThenOptionalEmpyIsReceivedAndContexStrategyConvertIsExecuted() {

        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setCaratula("tmpMaite.test");
        Contrato contratoTest = new Contrato();
        contratoTest.setPathDelArchivo("contrato.test");

        when(contextStrategyConverter.convertRegistry(tmpMaite)).thenReturn(Optional.empty());
        Optional<Contrato> contrato = converterService.convert(tmpMaite);

        verify(contextStrategyConverter,times(1)).convertRegistry(tmpMaite);
        assertThat(contrato.isEmpty()).isEqualTo(true);
    }
}
