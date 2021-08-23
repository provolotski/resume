package by.minsk.resume.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan({
        "by.minsk.resume.service.impl",
        "by.minsk.resume.controller",
        "by.minsk.resume.filter",
        "by.minsk.resume.listener"
})
public class ServiceConfig
{
    @Bean
      public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
          PropertySourcesPlaceholderConfigurer configurer= new PropertySourcesPlaceholderConfigurer();
          configurer.setLocations(getResources());
          return configurer;
      }

      private static Resource[] getResources(){
        return new Resource[]{new ClassPathResource("application.properties")};
      }
}
