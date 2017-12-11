package com.john.cena;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.john.cena.service.UserService;

@SpringBootApplication
@EnableOAuth2Client
public class CenaApplication extends WebSecurityConfigurerAdapter {
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@Autowired
	UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
			.authorizeRequests()
				.antMatchers("/", "/css/**", "/image/**", "/js/**")
				.permitAll()
			.anyRequest()
				.authenticated()
			.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and()
				.logout().logoutSuccessUrl("/").permitAll()
			.and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}
	
	@Bean
	@ConfigurationProperties("naver.client")
	public AuthorizationCodeResourceDetails naver() {
		return new AuthorizationCodeResourceDetails();
	}
	
	@Bean
	@ConfigurationProperties("naver.resource")
	public ResourceServerProperties naverResource() {
		return new ResourceServerProperties();
	}
	
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	
	/**
	 * 로그인 성공 후 DB에 신규 사용자 등록을 위해 ssoFilter를 추가함.
	 */
	private Filter ssoFilter() {
	  OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login");
	  OAuth2RestTemplate templete = new OAuth2RestTemplate(naver(), oauth2ClientContext);
	  filter.setRestTemplate(templete);
	  UserInfoTokenServices tokenServices = new UserInfoTokenServices(naverResource().getUserInfoUri(), naver().getClientId());
	  tokenServices.setRestTemplate(templete);
	  filter.setTokenServices(tokenServices);
	  filter.setAuthenticationSuccessHandler(successHandler());
	  return filter;
	}
	
	/**
	 * 인증 후 사용자 정보 등록 처리.
	 * 신규 사용자는 DB에 ID, NICKNAME 입력.
	 * 기존 사용자는 NICKNAME 업데이트
	 */
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new SimpleUrlAuthenticationSuccessHandler() {
	    	@Override
		    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
		    	Authentication authentication) throws ServletException, IOException {
	    		OAuth2Authentication auth = (OAuth2Authentication)authentication;
	    		// 사용자 정보 얻기
	    		@SuppressWarnings("unchecked")
				Map<String, String> userInfo = ((Map<String, Map<String, String>>)auth.getUserAuthentication().getDetails()).get("response");
	    		// 사용자 등록
	    		request.getSession().setAttribute("userInfo", userInfo);
	    		userService.registUser(userInfo);
		        super.onAuthenticationSuccess(request, response, authentication);
		    }
	    };
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CenaApplication.class, args);
	}
}
