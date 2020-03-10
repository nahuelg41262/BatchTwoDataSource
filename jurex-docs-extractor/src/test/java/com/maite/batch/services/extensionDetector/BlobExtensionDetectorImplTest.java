package com.maite.batch.services.extensionDetector;

import com.maite.batch.services.extensionDetector.impl.BlobExtensionDetectorImpl;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class BlobExtensionDetectorImplTest {

    @InjectMocks
    BlobExtensionDetectorImpl blobExtensionDetector;


    @Test
    public void givenBinaryPdfFile_WhenGetExtensionType_ThenPdfExtensionIsExpected() throws IOException, MagicParseException, MagicException, MagicMatchNotFoundException {
        //given
        InputStream in = new FileInputStream(new File("src/test/resources/pdf.test.pdf"));
        //when
        Optional<String> extensionType = blobExtensionDetector.getExtensionType(in.readAllBytes());
        //then
        assertThat(extensionType.get()).isEqualTo("pdf");
    }

    @Test
    public void givenBinaryTiffFile_WhenGetExtensionType_ThenTiffExtensionIsExpected() throws IOException, MagicParseException, MagicException, MagicMatchNotFoundException {
        //given
        InputStream in = new FileInputStream(new File("src/test/resources/tiff.test.tiff"));
        //when
        Optional<String> extensionType = blobExtensionDetector.getExtensionType(in.readAllBytes());
        //then
        assertThat(extensionType.get()).isEqualTo("tif");
    }


}
