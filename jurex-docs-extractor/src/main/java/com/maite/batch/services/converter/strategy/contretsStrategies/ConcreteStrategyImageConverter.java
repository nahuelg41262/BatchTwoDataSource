package com.maite.batch.services.converter.strategy.contretsStrategies;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.maite.batch.services.converter.strategy.IStrategyConverter;
import com.maite.batch.services.converter.strategy.contretsStrategies.utils.FilePathValidator;
import com.maite.batch.services.mapper.IMapperProcessorService;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;
import org.springframework.stereotype.Component;

@Component
public class ConcreteStrategyImageConverter implements IStrategyConverter<ConcreteStrategyImageConverter> {

    private IMapperProcessorService mapperService;
    private String pathOut;

    @Override
    public ConcreteStrategyImageConverter isMyImplementation(String tipoDeArchivo, String pathOut, IMapperProcessorService mapperService) {
        List<String> tiposDeArchivos = List.of("TIFF", "TIF", "JPEG", "PNG", "JPG");
        return tiposDeArchivos.stream().anyMatch(tipo -> tipoDeArchivo.toUpperCase().contains(tipo)) ? new ConcreteStrategyImageConverter(pathOut, mapperService) : null;
    }

    @Override
    public Optional<Contrato> convert(TMPMaite tmpMaite) throws Exception {
        Optional<Contrato> contrato;
        try {
            BufferedImage img = ImageIO.read(tmpMaite.getArchivo().getBinaryStream());

            String nombreHigenizado = FilePathValidator.cleanNameOfFile(tmpMaite.getCaratula());
            String pathDelArchivo = FilePathValidator.createNewFileNameInDirectory(nombreHigenizado, this.pathOut, tmpMaite.getExtension(), tmpMaite.getIdDocumento());

            ImageIO.write(img, tmpMaite.getExtension().toUpperCase(), new File(pathDelArchivo));

            contrato = Optional.of(mapperService.map(tmpMaite, pathDelArchivo));
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            contrato = Optional.empty();

        } catch (Exception e) {
            e.printStackTrace();
            contrato = Optional.empty();

        }

        return contrato;
    }

    public String getPathOut() {
        return pathOut;
    }

    public void setPathOut(String pathOut) {
        this.pathOut = pathOut;
    }

    public ConcreteStrategyImageConverter() {
    }

    private ConcreteStrategyImageConverter(String pathOut, IMapperProcessorService mapperService) {
        this.pathOut = pathOut;
        this.mapperService = mapperService;
    }
}
