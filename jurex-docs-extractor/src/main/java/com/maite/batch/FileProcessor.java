package com.maite.batch;

import com.maite.batch.services.converter.IConverterService;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class FileProcessor implements ItemProcessor<List<TMPMaite>, List<Optional<Contrato>>> {

    @Autowired
    IConverterService converterService;

    @Override
    public List<Optional<Contrato>>  process(List<TMPMaite> documentos) throws Exception, FileNotFoundException, IOException, SQLException {
        List<Optional<Contrato>> contratos = new ArrayList<>();
        documentos.forEach(documento -> {
            contratos.add(converterService.convert(documento));
        });

        return contratos.stream().filter(opt -> !opt.isEmpty()).collect(Collectors.toList());
    }


}
