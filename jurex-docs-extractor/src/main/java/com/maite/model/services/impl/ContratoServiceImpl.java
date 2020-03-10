package com.maite.model.services.impl;

import com.maite.model.entities.Contrato;
import com.maite.model.repositories.IContratoRepository;
import com.maite.model.services.IContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional("maiteTransactionManager")
public class ContratoServiceImpl implements IContratoService {

    @Autowired
    private IContratoRepository contratoRepository;


    @Override
    public void save(Contrato contrato) {
        contratoRepository.save(contrato);
    }

    @Override
    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }
}
