package com.maite.batch.services.converter.strategy;

import com.maite.batch.services.converter.strategy.contretsStrategies.ConcreteStrategyImageConverter;
import com.maite.batch.services.converter.strategy.contretsStrategies.ConcreteStrategyPdfConverter;
import com.maite.batch.services.mapper.impl.MapperProccessorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class ConcreteStrategyProviderTest {

    @Mock
    MapperProccessorImpl mapperProcessorService;

    @Test
    public void givenTypeOfFilePdf_WhenGetProperImplementation_ThenConcreteStrategyPdfConverterInstanceIsReturned() {
        Optional<IStrategyConverter> strategyConverter = ConcreteStrategyProvider
                .getProperImplementation("pdf", "/dirtest" ,mapperProcessorService);

        assertThat(strategyConverter.get()).isInstanceOf(ConcreteStrategyPdfConverter.class);
    }
    @Test
    public void givenTypeOfFilePdf_WhenGetProperImplementation_ThenConcreteStrategyImageConverterInstanceIsReturned() {
        Optional<IStrategyConverter> strategyConverter = ConcreteStrategyProvider
                .getProperImplementation("jPeG", "/dirtest" ,mapperProcessorService);

        assertThat(strategyConverter.get()).isInstanceOf(ConcreteStrategyImageConverter.class);
    }
    @Test
    public void givenTypeOfFileNotImplemented_WhenGetProperImplementation_ThenEmptyOptionalIsReturned() {
        Optional<IStrategyConverter> strategyConverter = ConcreteStrategyProvider
                .getProperImplementation("TipoNoImplementado", "/dirtest" ,mapperProcessorService);

        assertThat(strategyConverter.isEmpty()).isEqualTo(true);
    }
}
