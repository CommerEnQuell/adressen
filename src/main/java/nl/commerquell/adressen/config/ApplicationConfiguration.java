package nl.commerquell.adressen.config;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import nl.commerquell.adressen.ApplicationConstants;
import nl.commerquell.adressen.PageConstants;
import nl.commerquell.adressen.utils.Menko;

@Configuration
public class ApplicationConfiguration {
	private static final Logger logger = Logger.getLogger(ApplicationConfiguration.class.getName());
	
	@Autowired
	private Environment env;

	@Menko(subject="Is er een betere manier om applicatiebrede variabelen te declareren?")
	@Bean
	public ApplicationConstants applicationConstants() {
	
		String valueStr = env.getProperty("spring-data-rest.default-page-size", "10");
		int value = -1;
		try {
			value = Integer.valueOf(valueStr).intValue();
			if (value == 0) {
				value = Integer.MAX_VALUE;
			}
			if (value < 0) {
				value = 10;
				logger.warning("Default page size set to 10 because a value of " + value + " is not allowed");
			} else {
				logger.info("Default page size set to " + value);
			}
		} catch (Exception x) {
			value = 10;
		}
		
		ApplicationConstants retval = new ApplicationConstants();
		retval.setDefaultPageSize(value);
		
		return retval;
	}

}
