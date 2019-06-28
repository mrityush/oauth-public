package com.src.oauth2.config.oauth;

import com.src.oauth2.enums.LoginSignupType;
import com.src.oauth2.services.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 5/15/14.
 */
@EnableWebSecurity
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomSuccessHandler customSuccessHandler;


	@Autowired
	private DataSource datasource;

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	@Autowired
	private SocialService socialService;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean(name = "userDetailsService")
	public UserDetailsService userDetailsService() {
		JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
		jdbcDao.setDataSource(datasource);
		jdbcDao.setUsersByUsernameQuery(
				"select username,password,enabled from user_registration where username=?");
		jdbcDao.setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");
		return jdbcDao;
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//                .antMatchers("/**").permitAll();
//				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//				.antMatchers("/user/register**").hasAuthority("ROLE_ANONYMOUS")
//				.antMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_VENDOR")
//				.antMatchers("/vendor/**").hasAnyAuthority("ROLE_VENDOR", "ROLE_ADMIN")
//				.antMatchers("/moderator/**").access("hasRole('MODERATOR')")
				.antMatchers("/auth/**").hasAuthority("ROLE_ANONYMOUS")
				.anyRequest().permitAll()
				.and()
				.formLogin().usernameParameter("username").passwordParameter("password")
				.failureUrl("/login?error")
				.successHandler(customSuccessHandler)
//                        .defaultSuccessUrl("/admin/dashboard")
				.loginPage("/login")
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/?logout").permitAll()
				.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.exceptionHandling()
				.accessDeniedHandler(new AccessDeniedHandlerImpl())
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
				.and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**").antMatchers("/test/buy/response"); // like this
	}

	@Bean
	@ConfigurationProperties("facebook")
	public ClientResources facebook() {
		return new ClientResources(LoginSignupType.Facebook);
	}

	@Bean
	@ConfigurationProperties("google")
	public ClientResources google() {
		return new ClientResources(LoginSignupType.Google);
	}

	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook(), "/social/facebook"));
		filters.add(ssoFilter(google(), "/social/google"));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
//		client.getClient().setPreEstablishedRedirectUri("/auth/v1");
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
		OAuth2TokenService tokenServices = new OAuth2TokenService(client, socialService);
		tokenServices.setRestTemplate(oAuth2RestTemplate);
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl("/auth/v1/");
		oAuth2ClientAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
		return oAuth2ClientAuthenticationFilter;
	}

}

class ClientResources {

	private LoginSignupType LoginSignupType;

	ClientResources(LoginSignupType LoginSignupType) {
		this.LoginSignupType = LoginSignupType;
	}

	@NestedConfigurationProperty
	private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

	@NestedConfigurationProperty
	private OAuth2ResourceServerProperties resource = new OAuth2ResourceServerProperties();

	@NestedConfigurationProperty
	private OAuth2UserProperties entity = new OAuth2UserProperties();

	public AuthorizationCodeResourceDetails getClient() {
		return client;
	}

	public OAuth2ResourceServerProperties getResource() {
		return resource;
	}

	public OAuth2UserProperties getEntity() {
		return entity;
	}

	public LoginSignupType getLoginSignupType() {
		return LoginSignupType;
	}
}
