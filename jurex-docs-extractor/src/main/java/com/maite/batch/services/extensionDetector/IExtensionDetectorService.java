package com.maite.batch.services.extensionDetector;

import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* Servicio para la obtencion de la extension del archivo
* */
@Service
public interface IExtensionDetectorService {

    Optional<String> getExtensionType(byte[] input ) throws MagicParseException, MagicException, MagicMatchNotFoundException;

}
