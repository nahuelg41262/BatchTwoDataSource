package com.maite.batch.services.mapper;

import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.springframework.stereotype.Service;
/*
* R DTO que orquetara el pasaje de datos entre las entidades
* E entidad origen
* T entidad destino
* */
@Service
public interface IMapperProcessorService  {
    //Metodo que mapea los datos entre la entidades
    Contrato map(TMPMaite tmpMaite , String pathOut);
}
