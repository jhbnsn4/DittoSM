package dittoSM.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configurer class to add our login interceptor
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new LoginInterceptor());
	}
}
