package by.minsk.resume.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(EmailConfig.class);

    @Autowired
    private ConfigurableEnvironment environment;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(environment.getRequiredProperty("email.smtp.server"));
        if(environment.containsProperty("email.smtp.username")){
            LOGGER.debug("user name is: "+environment.resolveRequiredPlaceholders(environment.getRequiredProperty("email.smtp.username")));

            javaMailSender.setUsername(environment.resolveRequiredPlaceholders(environment.getRequiredProperty("email.smtp.username")));
            LOGGER.debug("password name is: "+environment.resolveRequiredPlaceholders(environment.getRequiredProperty("email.smtp.password")));

            javaMailSender.setPassword(environment.resolveRequiredPlaceholders(environment.getRequiredProperty("email.smtp.password")));
            javaMailSender.setPort(Integer.parseInt(environment.getRequiredProperty("email.smtp.port")));
            javaMailSender.setDefaultEncoding("UTF-8");
            javaMailSender.setJavaMailProperties(javaMailProperties());
        }
        return javaMailSender;
    }

    private Properties javaMailProperties(){
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.starttls.enable", "true");
        return p;
    }
}
