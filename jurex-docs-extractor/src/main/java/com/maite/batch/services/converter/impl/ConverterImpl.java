package com.maite.batch.services.converter.impl;

import com.maite.batch.services.converter.IConverterService;
import com.maite.batch.services.converter.strategy.ContextStrategyConverter;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConverterImpl implements IConverterService {

    @Autowired
    ContextStrategyConverter contextStrategyConverter;

    @Override
    public Optional<Contrato> convert(TMPMaite registro) {
        return contextStrategyConverter.convertRegistry(registro);
    }
}
