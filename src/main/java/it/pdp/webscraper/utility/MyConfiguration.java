package it.pdp.webscraper.utility;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = false)
public class MyConfiguration implements EnvironmentAware {

	private static Environment env;

	public static String getProperty(String key) {
		return env.getProperty(key);
	}

	@Override
	public void setEnvironment(Environment env) {
		MyConfiguration.env = env;
	}
}
