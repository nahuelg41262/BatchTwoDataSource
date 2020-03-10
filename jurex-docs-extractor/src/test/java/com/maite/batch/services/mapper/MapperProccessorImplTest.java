package com.maite.batch.services.mapper;

import com.maite.batch.services.mapper.impl.MapperProccessorImpl;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class MapperProccessorImplTest {

    @InjectMocks
    MapperProccessorImpl mapperProccessor;

    @Test
    public void givenTMPMaiteAndPathOut_WhenMap_ThenContratoIsReturned() {
        TMPMaite tmpamaite = new TMPMaite();
        tmpamaite.setIdDocumento("id.test");
        String pathOut = "pathOut/test";
        //When
        Contrato contrato = mapperProccessor.map(tmpamaite,pathOut);
        //then

        assertThat(contrato.getPathDelArchivo()).isEqualTo(pathOut);
        assertThat(contrato.getIdJurex()).isEqualTo(tmpamaite.getIdDocumento());
    }
    @Test
    public void givenTMPMaiteWithOutIdDocumentAndPathOut_WhenMap_ThenContratoIsReturned() {
        TMPMaite tmpamaite = new TMPMaite();
        String pathOut = "pathOut/test";
        //When
        Contrato contrato = mapperProccessor.map(tmpamaite,pathOut);
        //then

        assertThat(contrato.getPathDelArchivo()).isEqualTo(pathOut);
        assertThat(contrato.getIdJurex()).isEqualTo("NO_TIENE_ID_EN_JUREX");
    }
}
