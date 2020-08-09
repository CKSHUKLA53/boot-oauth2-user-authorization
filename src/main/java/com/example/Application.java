package com.example;

import java.security.Principal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableResourceServer
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/validateUser")
	public Principal user(Principal user) {
		return user;
	}

	@Configuration
	protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {
		
		@Autowired
		private DataSource dataSource;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource)
					.usersByUsernameQuery("select username, password, true as enabled" + " from user where username=?")
					.authoritiesByUsernameQuery("select username, 'ROLE_USER' as authority from user where username=?");
		}

	}
}
