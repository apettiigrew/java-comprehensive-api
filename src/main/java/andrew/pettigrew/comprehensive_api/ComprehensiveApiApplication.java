package andrew.pettigrew.comprehensive_api;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackageClasses = ComprehensiveApiApplication.class)
public class ComprehensiveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComprehensiveApiApplication.class, args);
	}

	/**
	 * Provides a primary ModelMapper bean to handle conversion of DTOs to and from Resources.
	 *
	 * @return a configured ModelMapper instance
	 */
	@Primary
	public ModelMapper modelMapper(){
		final var mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setFieldMatchingEnabled(true);

		return mapper;
	}

}
