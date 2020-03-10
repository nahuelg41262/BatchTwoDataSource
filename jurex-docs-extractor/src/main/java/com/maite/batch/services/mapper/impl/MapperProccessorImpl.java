package com.maite.batch.services.mapper.impl;

import com.maite.batch.services.mapper.IMapperProcessorService;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.springframework.stereotype.Component;

@Component
public class MapperProccessorImpl implements IMapperProcessorService {


    @Override
    public Contrato map(TMPMaite tmpMaite, String pathOut) {
        Contrato contrato = new Contrato();
        if (tmpMaite.getIdDocumento() == null) {
            contrato.setIdJurex("NO_TIENE_ID_EN_JUREX");
            contrato.setPathDelArchivo(pathOut);
        } else {
            contrato.setIdJurex(tmpMaite.getIdDocumento());
            contrato.setPathDelArchivo(pathOut);
        }

        return contrato;

    }
}
