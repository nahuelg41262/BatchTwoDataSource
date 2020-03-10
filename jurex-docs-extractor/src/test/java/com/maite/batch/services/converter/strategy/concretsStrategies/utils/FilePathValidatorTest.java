package com.maite.batch.services.converter.strategy.concretsStrategies.utils;

import com.maite.batch.services.converter.strategy.contretsStrategies.utils.FilePathValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Files.class)
public class FilePathValidatorTest {


    @Test
    public void givenFileNameExists_WhenCreateNewFileNameInDirectory_ThenFileNameWithIdDocumentoISReturned() {
        ClassLoader classLoader = getClass().getClassLoader();

        String currentDir = Paths.get("").toAbsolutePath().toString();
        String resultTest = FilePathValidator.createNewFileNameInDirectory("pdf.test", currentDir + "/src/test/resources/", "pdf", "IdDocumentoPrueba");

        assertThat(resultTest).isEqualTo("/home/finahuel/Docs-Extrator-REPO/jurex-docs-extractor/src/test/resources/pdf.test_IdDocumentoPrueba.pdf");
    }
    @Test
    public void givenFileNameDoesNotExists_WhenCreateNewFileNameInDirectory_ThenFileNameISReturned() {
        ClassLoader classLoader = getClass().getClassLoader();

        String currentDir = Paths.get("").toAbsolutePath().toString();
        String resultTest = FilePathValidator.createNewFileNameInDirectory("pdf.test.NoExiste", currentDir+"/src/test/resources/", "pdf", "IdDocumentoPrueba");

        assertThat(resultTest).isEqualTo("/home/finahuel/Docs-Extrator-REPO/jurex-docs-extractor/src/test/resources/pdf.test.NoExiste.pdf");
    }
}
