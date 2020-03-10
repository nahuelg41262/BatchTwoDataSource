package com.maite.batch.services.converter.strategy;

import com.maite.batch.services.mapper.IMapperProcessorService;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Clase proveedora de implementaciones de IStrategyConverter
 * */
public class ConcreteStrategyProvider {

    private static final Reflections reflections = new Reflections(IStrategyConverter.class.getPackage().getName());

    /*
     * Metodo encargado de retornar la implementacion adecuada segun el tipo de archivo
     *
     * */
    public static Optional<IStrategyConverter> getProperImplementation(String tipoDeArchivo, String pathOut, IMapperProcessorService mapperServie) {


        Set<Class<? extends IStrategyConverter>> implementaciones = reflections.getSubTypesOf(IStrategyConverter.class);

        List<IStrategyConverter> implementsExecuted = implementaciones.stream()
                .map(impl -> {
                    IStrategyConverter concreteStrategy;
                    try {
                        concreteStrategy = impl.getDeclaredConstructor().newInstance().isMyImplementation(tipoDeArchivo, pathOut, mapperServie);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        concreteStrategy = null;
                    }
                    return concreteStrategy;
                })
                .collect(Collectors.toList());

        return implementsExecuted.isEmpty()
                ? Optional.empty()
                : implementsExecuted.stream().filter(impl -> !Objects.isNull(impl)).findFirst();
    }

}
