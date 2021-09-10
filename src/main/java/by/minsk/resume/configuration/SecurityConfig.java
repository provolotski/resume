package by.minsk.resume.configuration;

 import by.minsk.resume.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.NoOpPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository  = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/my-profile", "/edit", "/edit/**", "/remove").hasAuthority(Constants.USER)
                .anyRequest().permitAll();
        http.formLogin()
                .loginPage("/sign-in")
                .loginProcessingUrl("/sign-in-handler")
                .usernameParameter("uid")
                .passwordParameter("password")
                .defaultSuccessUrl("/my-profile")
                .failureUrl("/sign-in-failed");
        http.logout()
                .logoutUrl("/sign-out")
                .logoutSuccessUrl("/welcome")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
        http.rememberMe()
                .rememberMeParameter("remember-me")
                .key("resume-online")
                .tokenRepository(persistentTokenRepository());
//        http.csrf().disable();

    }
}
