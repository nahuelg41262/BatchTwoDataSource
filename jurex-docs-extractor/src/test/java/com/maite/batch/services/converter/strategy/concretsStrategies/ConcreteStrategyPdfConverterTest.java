package com.maite.batch.services.converter.strategy.concretsStrategies;

import com.maite.batch.services.converter.strategy.contretsStrategies.ConcreteStrategyPdfConverter;
import com.maite.batch.services.converter.strategy.contretsStrategies.utils.FilePathValidator;
import com.maite.batch.services.mapper.impl.MapperProccessorImpl;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.assertj.core.util.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(PowerMockRunner.class)
@PrepareForTest({FilePathValidator.class})
public class ConcreteStrategyPdfConverterTest {


    @InjectMocks
    ConcreteStrategyPdfConverter concreteStrategyPdfConverter;
    @Mock
    MapperProccessorImpl mapperProcessorService;

    @Test
    public void givenIncompatibleTipoDeArchivo_whenIsMyImplementatio_thenNullIsReturned() {
        String tipoDeArchivoNoCompatible = "png";

        ConcreteStrategyPdfConverter concreteStrategyPdfConverter = this.concreteStrategyPdfConverter.isMyImplementation(tipoDeArchivoNoCompatible, "pathOut", mapperProcessorService);

        assertThat(concreteStrategyPdfConverter).isEqualTo(null);
    }

    @Test
    public void givenIncompatibleTipoDeArchivo_whenIsMyImplementatio_thenconcreteStrategyPdfConverterInstanceIsReturned() {
        String tipoDeArchivoCompatible = "pdf";

        ConcreteStrategyPdfConverter concreteStrategyPdfConverter = this.concreteStrategyPdfConverter.isMyImplementation(tipoDeArchivoCompatible, "pathOut", mapperProcessorService);

        assertThat(concreteStrategyPdfConverter).isInstanceOf(ConcreteStrategyPdfConverter.class);

    }

    @Test
    public void givenTMPMaite_WhenConvert_ThenFileIsCreatedAndContratoIsReturned() throws Exception {
        //given
        InputStream in = new FileInputStream(new File("src/test/resources/pdf.test.pdf"));
        Blob blob = new SerialBlob(in.readAllBytes());

        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setArchivo(blob);
        tmpMaite.setCaratula("prueba.test");
        tmpMaite.setExtension(".pdf");
        tmpMaite.setIdDocumento("prueba.testID");

        String pathOut = "src/test/resources/";
        Contrato contrato = new Contrato();
        contrato.setPathDelArchivo("prueba");
        concreteStrategyPdfConverter.setPathOut(pathOut);

        String pathDelArchivo = pathOut + tmpMaite.getCaratula() + tmpMaite.getExtension();
        //when
        PowerMockito.mockStatic(FilePathValidator.class);
        PowerMockito.when(FilePathValidator.cleanNameOfFile(tmpMaite.getCaratula())).thenReturn(tmpMaite.getCaratula());
        PowerMockito.when(FilePathValidator.createNewFileNameInDirectory(tmpMaite.getCaratula(), pathOut, "pdf", tmpMaite.getIdDocumento())).thenReturn(pathDelArchivo);
        Mockito.when(mapperProcessorService.map(tmpMaite, pathDelArchivo)).thenReturn(contrato);
        Optional<Contrato> contratoReturned = concreteStrategyPdfConverter.convert(tmpMaite);

        File fileCreated = new File(pathDelArchivo);
        //then
        assertThat(fileCreated.exists()).isEqualTo(true);
        assertThat(contratoReturned.get()).isEqualTo(contrato);
        //se elimina el archivo
        Files.delete(new File(pathDelArchivo));

    }
}
