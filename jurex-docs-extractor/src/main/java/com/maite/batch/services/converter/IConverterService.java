package com.maite.batch.services.converter;

import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;

import java.util.Optional;

public interface IConverterService {

    Optional<Contrato> convert(TMPMaite registro);
}
