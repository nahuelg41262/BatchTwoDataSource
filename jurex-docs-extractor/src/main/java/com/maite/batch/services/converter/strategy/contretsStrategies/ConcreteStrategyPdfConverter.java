package com.maite.batch.services.converter.strategy.contretsStrategies;

import com.maite.batch.services.converter.strategy.IStrategyConverter;
import com.maite.batch.services.converter.strategy.contretsStrategies.utils.FilePathValidator;
import com.maite.batch.services.mapper.IMapperProcessorService;
import com.maite.model.entities.Contrato;
import com.maite.jurex.model.entities.TMPMaite;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class ConcreteStrategyPdfConverter implements IStrategyConverter<ConcreteStrategyPdfConverter> {

    private IMapperProcessorService mapperService;
    private String pathOut;

    @Override
    public ConcreteStrategyPdfConverter isMyImplementation(String tipoDeArchivo, String pathOut, IMapperProcessorService mapperService) {
        return tipoDeArchivo.toUpperCase().contains("PDF") ? new ConcreteStrategyPdfConverter(pathOut, mapperService) : null;
    }

    @Override
    public Optional<Contrato> convert(TMPMaite tmpMaite) throws Exception {
        Optional<Contrato> contrato;
        try (PDDocument document = PDDocument.load(tmpMaite.getArchivo().getBinaryStream());) {

            String nombreHigenizado = FilePathValidator.cleanNameOfFile(tmpMaite.getCaratula());
            String pathDelArchivo = FilePathValidator.createNewFileNameInDirectory(nombreHigenizado, this.pathOut, "pdf", tmpMaite.getIdDocumento());

            document.save(pathDelArchivo);

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

    public ConcreteStrategyPdfConverter() {
    }

    public ConcreteStrategyPdfConverter(String pathOut, IMapperProcessorService mapperService) {
        this.pathOut = pathOut;
        this.mapperService = mapperService;
    }


    public String getPathOut() {
        return pathOut;
    }

    public void setPathOut(String pathOut) {
        this.pathOut = pathOut;
    }
}
