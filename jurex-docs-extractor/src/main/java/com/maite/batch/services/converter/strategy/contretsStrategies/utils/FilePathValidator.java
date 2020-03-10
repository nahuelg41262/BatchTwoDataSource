package com.maite.batch.services.converter.strategy.contretsStrategies.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FilePathValidator {
    /*
     * Valida si la el archivo existe , en tal caso contena su id
     * */
    public static String createNewFileNameInDirectory(String nombreDelArchivo, String pathOut, String extension, String idDocumento) {
        //Se llama recursivamente hasta que su nombre no exista en el directorio .
        if (Files.exists(Paths.get(pathOut + nombreDelArchivo +"."+extension))) {
            return createNewFileNameInDirectory(nombreDelArchivo.concat("_" + idDocumento), pathOut, extension, idDocumento);
        } else {
            return pathOut + nombreDelArchivo + "." + extension;
        }
    }

    public static String cleanNameOfFile(String nombreDelArchivo) {

        return nombreDelArchivo.trim().replace("/", "");
    }
}
