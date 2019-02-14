package nl.commerquell.adressen.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource securityDataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
					.antMatchers("/adressenboek/").hasAnyRole("DUMBO", "READER", "USER", "ADMIN")
        			.antMatchers("/adressenboek/personen/**").hasAnyRole("USER", "ADMIN")
        			.antMatchers("/adressenboek/adressen/**").hasAnyRole("USER", "ADMIN")
        			.antMatchers("/adressenboek/query/**").hasAnyRole("READER", "USER", "ADMIN")
        			.antMatchers("/adressenboek/users/**").hasRole("ADMIN")
					.antMatchers("/adressenboek/password/**").hasAnyRole("DUMBO", "READER", "USER", "ADMIN")
					.antMatchers("/actuator/**").hasAnyRole("MONITOR", "ADMIN")
      			.and()
        			.formLogin()
        				.loginPage("/adressenboek/login/")
        				.loginProcessingUrl("/authenticateTheUser")
        				.defaultSuccessUrl("/adressenboek/")
        				.permitAll()
        			.and()
        			.logout()
        			.logoutSuccessUrl("/adressenboek/login/")
//        			.antMatchers("/")
        			.permitAll()
        			.and()
        			.exceptionHandling().accessDeniedPage("/adressenboek/login/access-denied");
    }
}