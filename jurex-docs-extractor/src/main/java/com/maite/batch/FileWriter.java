package com.maite.batch;

import java.util.List;
import java.util.Optional;

import com.maite.model.services.IContratoService;
import org.springframework.batch.item.ItemWriter;

import com.maite.model.entities.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileWriter implements ItemWriter<List<Optional<Contrato>>> {

    private IContratoService contratoService;

    public FileWriter(IContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @Override
    public void write(List<? extends List<Optional<Contrato>>> contratos) throws Exception {
        contratos.forEach(list -> list.forEach(optionalContratos -> {
            optionalContratos.ifPresent(contratoService::save);
        }));
    }
}
