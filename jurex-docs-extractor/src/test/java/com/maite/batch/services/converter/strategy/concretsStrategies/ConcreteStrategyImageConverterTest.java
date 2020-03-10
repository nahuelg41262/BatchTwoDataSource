package com.maite.batch.services.converter.strategy.concretsStrategies;

import com.maite.batch.services.converter.strategy.contretsStrategies.ConcreteStrategyImageConverter;
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
import org.springframework.test.context.junit4.SpringRunner;

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
public class ConcreteStrategyImageConverterTest {

    @InjectMocks
    ConcreteStrategyImageConverter concreteStrategyImageConverter;
    @Mock
    MapperProccessorImpl mapperProcessorService;

    @Test
    public void givenIncompatibleTipoDeArchivo_whenIsMyImplementatio_thenNullIsReturned() {
        String tipoDeArchivoNoCompatible = "ter";

        ConcreteStrategyImageConverter concreteStrategyImageConverter = this.concreteStrategyImageConverter.isMyImplementation(tipoDeArchivoNoCompatible,"pathout", mapperProcessorService);

        assertThat(concreteStrategyImageConverter).isEqualTo(null);
    }

    @Test
    public void givenIncompatibleTipoDeArchivo_whenIsMyImplementatio_thenconcreteStrategyImageConverterInstanceIsReturned() {
        String tipoDeArchivoNoCompatible = "jpeg";

        ConcreteStrategyImageConverter concreteStrategyImageConverter = this.concreteStrategyImageConverter.isMyImplementation(tipoDeArchivoNoCompatible,"pathout", mapperProcessorService);

        assertThat(concreteStrategyImageConverter).isInstanceOf(ConcreteStrategyImageConverter.class);

    }

    @Test
    public void givenTMPMaite_WhenConvert_ThenFileIsCreatedAndContratoIsReturned() throws Exception {
        //given
        InputStream in = new FileInputStream(new File("src/test/resources/tiff.test.tiff"));
        Blob blob = new SerialBlob(in.readAllBytes());

        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setArchivo(blob);
        tmpMaite.setCaratula("prueba.test");
        tmpMaite.setExtension("tiff");
        tmpMaite.setIdDocumento("prueba.testID");

        String pathOut="src/test/resources/";
         Contrato contrato =new Contrato();
        contrato.setPathDelArchivo("prueba");
        concreteStrategyImageConverter.setPathOut(pathOut);

        String pathDelArchivo = pathOut + tmpMaite.getCaratula() + tmpMaite.getExtension();

        //when
        PowerMockito.mockStatic(FilePathValidator.class);
        PowerMockito.when(FilePathValidator.cleanNameOfFile(tmpMaite.getCaratula())).thenReturn(tmpMaite.getCaratula());
        PowerMockito.when(FilePathValidator.createNewFileNameInDirectory(tmpMaite.getCaratula(), pathOut, "tiff" , tmpMaite.getIdDocumento())).thenReturn(pathDelArchivo);
        Mockito.when(mapperProcessorService.map(tmpMaite, pathDelArchivo)).thenReturn(contrato);
        Optional<Contrato> contratoReturned = concreteStrategyImageConverter.convert(tmpMaite);

        File fileCreated = new File(pathDelArchivo);
        //then
       assertThat(fileCreated.exists()).isEqualTo(true);
       assertThat(contratoReturned.get()).isEqualTo(contrato);
        //se elimina el archivo c
        Files.delete(new File(pathOut+tmpMaite.getCaratula()+tmpMaite.getExtension()));
    }
}
