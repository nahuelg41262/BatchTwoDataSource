package com.maite.batch.services.extensionDetector.impl;

import com.maite.batch.services.extensionDetector.IExtensionDetectorService;
import net.sf.jmimemagic.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
 * Implementacion para archivos de tipo Blob
 * */
@Component
public class BlobExtensionDetectorImpl implements IExtensionDetectorService {

    @Override
    public Optional<String> getExtensionType(byte[] input) throws MagicParseException, MagicException, MagicMatchNotFoundException {
        try {
            MagicMatch match = Magic.getMagicMatch(input, true);
            return match != null ? Optional.of(match.getExtension()) : Optional.empty();
        }catch (MagicParseException | MagicException | MagicMatchNotFoundException ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
