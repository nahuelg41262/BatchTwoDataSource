package com.maite.batch.services.converter.strategy;

import com.maite.batch.services.converter.strategy.contretsStrategies.ConcreteStrategyPdfConverter;
import com.maite.batch.services.extensionDetector.IExtensionDetectorService;
import com.maite.batch.services.mapper.impl.MapperProccessorImpl;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import com.maite.properties.ConverterProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
 * PowerMockRunner para poder mockear metodos estaticos
 * */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ConcreteStrategyProvider.class})
public class ContextStrategyConverterTest {
    @InjectMocks
    ContextStrategyConverter contextStrategyConverter;
    @Mock
    ConverterProperties converterProperties;
    @Mock
    ConcreteStrategyPdfConverter concreteStrategyPdfConverter;
    @Mock
    IExtensionDetectorService extensionDetectorService;
    @Mock
    MapperProccessorImpl mapperProcessorService;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(contextStrategyConverter, "pathOut", "/dirTest");
    }

    @Test
    public void givenTMPMaite_WhenConvert_ThenContratoAndCorrectStrategyIsReturned() throws Exception {
        //Given
        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setNombreAdjunto("nombreadjunto.test");
        Contrato contratoTest = new Contrato();
        contratoTest.setPathDelArchivo("/dirTest");
        //When
        when(concreteStrategyPdfConverter.convert(tmpMaite)).thenReturn(Optional.of(contratoTest));
        PowerMockito.mockStatic(ConcreteStrategyProvider.class);
        PowerMockito.when(ConcreteStrategyProvider.getProperImplementation("nombreadjunto.test", "/dirTest", mapperProcessorService)).thenReturn(Optional.of(concreteStrategyPdfConverter));

        Optional<Contrato> contrato = contextStrategyConverter.convertRegistry(tmpMaite);
        //Then
        assertThat(contrato.get().getPathDelArchivo()).isEqualTo("/dirTest");
        assertThat(contextStrategyConverter.getConcretStrategyConverter()).isInstanceOf(ConcreteStrategyPdfConverter.class);
    }

    @Test
    public void givenTMPMaiteWithOutImplmentation_WhenConvert_ThenContratoIsEmptyIsReturned() throws Exception {
        //Given
        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setNombreAdjunto("nombreadjunto.test");
        Contrato contratoTest = new Contrato();
        contratoTest.setPathDelArchivo("/dirTest");
        //When
        when(concreteStrategyPdfConverter.convert(tmpMaite)).thenReturn(Optional.of(contratoTest));
        PowerMockito.mockStatic(ConcreteStrategyProvider.class);
        PowerMockito.when(ConcreteStrategyProvider.getProperImplementation("nombreadjunto.test", "/dirTest", mapperProcessorService)).thenReturn(Optional.empty());

        Optional<Contrato> contrato = contextStrategyConverter.convertRegistry(tmpMaite);
        //Then
        assertThat(contrato.isEmpty()).isEqualTo(true);
    }

    @Test
    public void givenTMPMaiteCanNotBeProcess_WhenConvert_TheContratoIsEmpty() throws Exception {
        //Given
        InputStream in = new FileInputStream(new File("src/test/resources/pdf.test.pdf"));
        Blob blob = new SerialBlob(in.readAllBytes());
        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setNombreAdjunto("nombreadjunto.test");
        tmpMaite.setArchivo(blob);


        //When
        when(concreteStrategyPdfConverter.convert(tmpMaite)).thenThrow(new Exception());
        //Se devuelve un Optional vacio para que entre por el catch
        when(extensionDetectorService.getExtensionType(any(blob.getBinaryStream().readAllBytes().getClass()))).thenReturn(Optional.empty());
        PowerMockito.mockStatic(ConcreteStrategyProvider.class);
        PowerMockito.when(ConcreteStrategyProvider.getProperImplementation("nombreadjunto.test", "/dirTest", mapperProcessorService)).thenReturn(Optional.of(concreteStrategyPdfConverter));

        Optional<Contrato> contrato = contextStrategyConverter.convertRegistry(tmpMaite);
        //Then
        assertThat(contrato.isEmpty()).isEqualTo(true);
        assertThat(contextStrategyConverter.isReProcess()).isEqualTo(false);
    }

    @Test
    public void GivenTMPMaiteWithIncorrectExtension_WhenConvertRegistryIsExcuted_ThenConvertRegistryTwoTimesIsExecutedAndContratoIsReturned() throws Exception {
        //Given
        InputStream in = new FileInputStream(new File("src/test/resources/pdf.test.pdf"));
        Blob blob = new SerialBlob(in.readAllBytes());
        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setNombreAdjunto("nombreadjunto.test");
        tmpMaite.setArchivo(blob);
        Contrato contratoTest = new Contrato();
        contratoTest.setPathDelArchivo("/dirTest");

        ContextStrategyConverter  spyConcreteStrategyConverter =  PowerMockito.spy(contextStrategyConverter);


        //When
        AtomicInteger counterToDiferentsReturns = new AtomicInteger();
        // Se retorna dos veces  debido a la recursion del metodo "convertRegistry"
        when(concreteStrategyPdfConverter.convert(tmpMaite)).thenAnswer(invocation -> {
            counterToDiferentsReturns.getAndIncrement();
            return counterToDiferentsReturns.get() == 1 ? new Exception() : Optional.of(contratoTest);
        });
        //Se devuelve un Optional vacio para que entre por el catch
        when(extensionDetectorService.getExtensionType(any(blob.getBinaryStream().readAllBytes().getClass()))).thenReturn(Optional.of("pdf"));
        PowerMockito.mockStatic(ConcreteStrategyProvider.class);
        PowerMockito.when(ConcreteStrategyProvider.getProperImplementation("nombreadjunto.test", "/dirTest", mapperProcessorService)).thenReturn(Optional.of(concreteStrategyPdfConverter));
        PowerMockito.when(ConcreteStrategyProvider.getProperImplementation("pdf", "/dirTest", mapperProcessorService)).thenReturn(Optional.of(concreteStrategyPdfConverter));

        Optional<Contrato> contrato = spyConcreteStrategyConverter.convertRegistry(tmpMaite);

        //Then
        assertThat(contrato.get().getPathDelArchivo()).isEqualTo("/dirTest");
        verify(spyConcreteStrategyConverter, times(2)).convertRegistry(any(TMPMaite.class));
        assertThat(spyConcreteStrategyConverter.getConcretStrategyConverter()).isInstanceOf(ConcreteStrategyPdfConverter.class);

    }
    @Test
    public void GivenTMPMaiteReProcess1Time_WhenConvertRegistryIsExecuted1MoreTime_ThenTheRecursionIsStopedAndEmptyOptinalIsReturned() throws Exception {
        //Given
        InputStream in = new FileInputStream(new File("src/test/resources/pdf.test.pdf"));
        Blob blob = new SerialBlob(in.readAllBytes());
        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setNombreAdjunto("nombreadjunto.test");
        tmpMaite.setArchivo(blob);
        Contrato contratoTest = new Contrato();
        contratoTest.setPathDelArchivo("/dirTest");

        ContextStrategyConverter  spyConcreteStrategyConverter =  PowerMockito.spy(contextStrategyConverter);

        //When
        AtomicInteger counterToDiferentsReturns = new AtomicInteger();
        // Se retorna dos veces  debido a la recursion del metodo "convertRegistry"
        when(concreteStrategyPdfConverter.convert(tmpMaite)).thenAnswer(invocation -> new Exception());
        //Se devuelve un Optional vacio para que entre por el catch
        when(extensionDetectorService.getExtensionType(any(blob.getBinaryStream().readAllBytes().getClass()))).thenReturn(Optional.of("pdf"));
        PowerMockito.mockStatic(ConcreteStrategyProvider.class);
        PowerMockito.when(ConcreteStrategyProvider.getProperImplementation("nombreadjunto.test", "/dirTest", mapperProcessorService)).thenReturn(Optional.of(concreteStrategyPdfConverter));
        PowerMockito.when(ConcreteStrategyProvider.getProperImplementation("pdf", "/dirTest", mapperProcessorService)).thenReturn(Optional.of(concreteStrategyPdfConverter));

        Optional<Contrato> contrato = spyConcreteStrategyConverter.convertRegistry(tmpMaite);

        //Then
        assertThat(contrato.isEmpty()).isEqualTo(true);
        ArgumentCaptor<TMPMaite> argument = ArgumentCaptor.forClass(TMPMaite.class);

        verify(spyConcreteStrategyConverter, times(2)).convertRegistry(argument.capture());

    }

}

