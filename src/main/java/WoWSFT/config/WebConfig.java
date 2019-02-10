package WoWSFT.config;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * Created by Qualson-Lee on 2016-08-04.
 */
@Configuration
@ComponentScan("WoWSFT")
public class WebConfig extends WebMvcConfigurationSupport
{
    @Bean
    public ViewResolver viewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(customMJ2HMC());
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public CustomObjectMapper createObjectMapper()
    {
        return new CustomObjectMapper(new MappingJsonFactory());
    }

    @Bean
    public CustomMJ2HMC customMJ2HMC()
    {
        CustomMJ2HMC customMJ2HMC = new CustomMJ2HMC();
        customMJ2HMC.setObjectMapper(new CustomObjectMapper(new MappingJsonFactory()));
        return customMJ2HMC;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }
}
