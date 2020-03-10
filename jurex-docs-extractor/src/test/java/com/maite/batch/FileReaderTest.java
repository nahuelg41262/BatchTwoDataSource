package com.maite.batch;

import com.maite.batch.FileReader;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.jurex.model.services.impl.TMPMaiteServiceImpl;
import com.maite.model.entities.Contrato;
import com.maite.model.services.impl.ContratoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FileReaderTest {
    @MockBean
    TMPMaiteServiceImpl itmpMaiteService;
    @MockBean
    ContratoServiceImpl contratoService;
    @Test
    public void givenTMPMaiteList_WhenRead_ThenTMPMAiteListIsRetreived() throws SQLException {
        //given
        FileReader fileReader = new FileReader(itmpMaiteService , contratoService);
        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setCaratula("tmpMaite.test");
        tmpMaite.setIdDocumento("documentoId.test");
        Blob blob = new SerialBlob("blob.test".getBytes());
        tmpMaite.setArchivo(blob);
        List<TMPMaite> tmpMaiteList = List.of(tmpMaite);

        //when
        when(itmpMaiteService.findAll(0, 200)).thenReturn(tmpMaiteList);
        List<TMPMaite> registros = fileReader.read();

        //then
        assertThat(registros.size()).isEqualTo(1);
        assertThat(registros.get(0).getCaratula()).isEqualTo(tmpMaite.getCaratula());
    }
    @Test
    public void givenTMPMaiteListWithOutArchivo_WhenRead_ThenListIsNotProcess() throws SQLException {
        //given
        FileReader fileReader = new FileReader(itmpMaiteService , contratoService);

        List<TMPMaite> tmpMaiteList = List.of(new TMPMaite(), new TMPMaite(), new TMPMaite(), new TMPMaite());

        //when
        when(itmpMaiteService.findAll(0, 200)).thenReturn(tmpMaiteList);
        List<TMPMaite> registros = fileReader.read();

        //then
        assertThat(registros.isEmpty()).isEqualTo(true);
    }


    @Test
    public void givenEmpyTMPMaiteList_WhenRead_ThenNullIsRetreived() {
        //given
        FileReader fileReader = new FileReader(itmpMaiteService ,contratoService );
        TMPMaite tmpMaite = new TMPMaite();
        tmpMaite.setCaratula("tmpMaite.test");
        List<TMPMaite> tmpMaiteList = List.of();

        //when
        when(itmpMaiteService.findAll(0, 200)).thenReturn(tmpMaiteList);
        List<TMPMaite> registros = fileReader.read();

        //then
        assertThat(registros).isEqualTo(null);
    }

}
