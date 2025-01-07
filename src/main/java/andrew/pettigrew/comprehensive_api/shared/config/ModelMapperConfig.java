package andrew.pettigrew.comprehensive_api.shared.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean("modelMapper")
    public ModelMapper ModelMapper() {
        return new ModelMapper();
    }

    @Bean("skipNullModelMapper")
    public ModelMapper skipNullModelMapper() {
        final var modelMapper = new ModelMapper();

        // Configure ModelMapper
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
