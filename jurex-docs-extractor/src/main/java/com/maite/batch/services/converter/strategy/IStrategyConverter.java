package com.maite.batch.services.converter.strategy;

import com.maite.batch.services.mapper.IMapperProcessorService;
import com.maite.jurex.model.entities.TMPMaite;
import com.maite.model.entities.Contrato;

import java.util.Optional;

/*
 * Interfaz generica para la conversion de Blob a Archivos
 *
 * Las Estrategias deben implementar un constructor con la siguiente firma de parametros
 * ConcreteStrategy(String pathOut, IMapperProcessorService mapperService)
 * */
public interface IStrategyConverter<T extends IStrategyConverter> {

    //Metodo que retorna una instancia de la implementacion con los parametros necesarios, si la misma corresponde al tipo de archivo .
    T isMyImplementation(String tipoDeArchivo , String pathOut , IMapperProcessorService mapperService);

    //Metodo encargado de hacer la conversion del archivo Blob
    Optional<Contrato> convert(TMPMaite tmpMaite) throws Exception;

}
