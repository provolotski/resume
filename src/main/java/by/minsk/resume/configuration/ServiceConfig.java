package by.minsk.resume.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;



@Configuration
@ComponentScan({ "by.minsk.resume.service.impl",
   //     "by.minsk.resume.controller",
        "by.minsk.resume.filter",
     //   "by.minsk.resume.listener",
        "by.minsk.resume.component.impl"})
public class ServiceConfig {
    @Bean
      public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() throws IOException {
          PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
          conf.setLocations(getResources());
          return conf;
      }

      private static Resource[] getResources(){
        return new Resource[] {new ClassPathResource("application.properties"), new ClassPathResource("logic.properties")};
      }
}
