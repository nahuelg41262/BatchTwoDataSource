package com.maite.model.services;

import com.maite.model.entities.Contrato;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IContratoService {
	
    void save(Contrato contrato);

    List<Contrato> findAll();
}
