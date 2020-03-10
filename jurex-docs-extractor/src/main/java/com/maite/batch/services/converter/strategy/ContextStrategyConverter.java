package com.maite.batch.services.converter.strategy;

import com.maite.batch.services.extensionDetector.IExtensionDetectorService;
import com.maite.batch.services.mapper.IMapperProcessorService;
import com.maite.model.entities.Contrato;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maite.jurex.model.entities.TMPMaite;
import com.maite.properties.ConverterProperties;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/*
 * Clase que decide que comportamiento se va a tomar deacuerdo  al archivo ingresado .
 * */
@Component
public class ContextStrategyConverter {
    private static final Logger LOGGER = LogManager.getLogger(ContextStrategyConverter.class);
    //indica que es registro indicado se esta reprocesasando
    private boolean reProcess = false;
    int counterReProcess = 0;
    private String pathOut;
    private Optional<Contrato> contrato;
    private IMapperProcessorService mapperService;
    private IExtensionDetectorService extensionDetectorService;

    private IStrategyConverter concretStrategyConverter;

    public Optional<Contrato> convertRegistry(TMPMaite registro) {
        ConcreteStrategyProvider
                .getProperImplementation(registro.getNombreAdjunto(), this.pathOut, this.mapperService)
                .ifPresentOrElse(converter -> {
                    this.setConverterProccessorServiceStrategy(converter);
                    try {
                        this.setContrato(converter.convert(registro));
                        this.reProcess = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.info("La extension para el resgistro con el id : {} es incorrecta , se intetara obenter su extension desde el Blob. ", registro.getIdDocumento());
                        try {
                            //En caso un error se intenta obtener la extension del archivo atravez del blob y reporcesarlo una vez mas
                            if (!this.reProcess) {
                                registro.setNombreAdjunto(
                                        this.extensionDetectorService.getExtensionType(registro.getArchivo().getBinaryStream().readAllBytes())
                                                .orElseThrow(MagicException::new)
                                );
                                this.counterReProcess++;
                                this.reProcess = true;
                            } else {
                                throw new MagicException();
                            }
                        } catch (MagicParseException | MagicMatchNotFoundException | SQLException | IOException | MagicException ex) {
                            //No se pudo obtener la extension del archivo
                            ex.printStackTrace();
                            LOGGER.error("No se puedo obtener la extension del archivo con el id : {} . El registro no fue procesado.", registro.getIdDocumento());
                            this.setContrato(Optional.empty());
                            this.reProcess = false;
                        }
                    }
                }, () -> {
                    //no hay implementaciones para el tipo de contrato
                    this.setContrato(Optional.empty());
                    LOGGER.error("El registro con el id : {} no fue procesado. Tipo de archivo no reconocido.", registro.getIdDocumento());
                    this.reProcess = false;
                });
        if (this.reProcess && this.counterReProcess <= 1) {
            //el registro se reprocesa por primera vez
            return this.convertRegistry(registro);
        } else {
            this.counterReProcess = 0;
            return this.contrato;
        }
    }

    /*
     * Metodo que setea la implementacion concreta de IStrategyConverter
     * */
    private void setConverterProccessorServiceStrategy(IStrategyConverter converterProccessorService) {
        this.concretStrategyConverter = converterProccessorService;
    }

    @Autowired
    public ContextStrategyConverter(ConverterProperties converterProperties, IMapperProcessorService mapperService, IExtensionDetectorService extensionDetectorService) {
        this.pathOut = converterProperties.getPathOut();
        this.mapperService = mapperService;
        this.extensionDetectorService = extensionDetectorService;
    }

    public IStrategyConverter getConcretStrategyConverter() {
        return concretStrategyConverter;
    }

    public Optional<Contrato> getContrato() {
        return contrato;
    }

    public void setContrato(Optional<Contrato> contrato) {
        this.contrato = contrato;
    }

    public String getPathOut() {
        return pathOut;
    }

    public void setPathOut(String pathOut) {
        this.pathOut = pathOut;
    }

    public IMapperProcessorService getMapperService() {
        return mapperService;
    }

    public void setMapperService(IMapperProcessorService mapperService) {
        this.mapperService = mapperService;
    }

    public boolean isReProcess() {
        return reProcess;
    }
}
