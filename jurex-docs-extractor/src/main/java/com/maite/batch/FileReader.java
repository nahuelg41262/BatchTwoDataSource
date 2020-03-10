package com.maite.batch;

import com.maite.jurex.model.entities.TMPMaite;
import com.maite.jurex.model.services.ITMPMaiteService;
import com.maite.model.entities.Contrato;
import com.maite.model.services.IContratoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader implements ItemReader<List<TMPMaite>> {

    private IContratoService contratoService;
    private ITMPMaiteService tmpMaiteService;
    private int page;
    private static final int PAGE_SIZE = 200;
    private static final Logger LOGGER = LogManager.getLogger(FileReader.class);
    private List<String> idcontratosYaProcesados;

    public FileReader() {
        initialize();
    }

    public FileReader(ITMPMaiteService tmpMaiteService, IContratoService contratoService) {
        this.tmpMaiteService = tmpMaiteService;
        this.contratoService = contratoService;
        this.page = 0;
        initialize();

    }

    public void initialize() {
        this.idcontratosYaProcesados = this.contratoService.findAll().stream()
                .map(Contrato::getIdJurex)
                .collect(Collectors.toList());
    }

    public synchronized List<TMPMaite> read() {
        List<TMPMaite> listRead;
        //Se filtran los contratos que ya fueron porcesados
        listRead = tmpMaiteService.findAll(page++, PAGE_SIZE).stream()
                .filter(tmpMaite -> !this.idcontratosYaProcesados.contains(tmpMaite.getIdDocumento()))
                .collect(Collectors.toList());

        listRead.stream()
                .filter(tmpMaite -> tmpMaite.getArchivo() == null)
                .collect(Collectors.toList())
                .forEach(tmpMaiteSinArchivo -> {
                    LOGGER.error("El registro con el id: {} y la caratula {} no fue procesado debido a que no existe el campo archivo ", tmpMaiteSinArchivo.getIdDocumento(), tmpMaiteSinArchivo.getCaratula());
                });

        return !listRead.isEmpty()
                ? listRead.stream().filter(tmpMaite -> tmpMaite.getArchivo() != null).collect(Collectors.toList())
                : null;
    }

}
