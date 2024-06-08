package com.livinservices.ProjectBoilerPlate.Extras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authorize) ->
						authorize
								.requestMatchers("/**", "/index").permitAll()
								.requestMatchers("/admin_section").hasRole("ADMIN")
								.requestMatchers( "/dashboard" ).authenticated()
				)
				.formLogin(
						form -> form
								.loginPage("/login")
								.loginProcessingUrl("/login")
								.defaultSuccessUrl("/dashboard")
								.permitAll()
				)
				.logout(
						logout -> logout
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
								.logoutSuccessUrl("/login?logout")
								.clearAuthentication(true)

								.invalidateHttpSession(true) // Invalidate session on logout
								.deleteCookies("JSESSIONID")
				).sessionManagement((session) -> session
						.sessionCreationPolicy( SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation().newSession() // Session fixation protection
						.maximumSessions(1) // Allow only one session per user
						.maxSessionsPreventsLogin(true) // Prevent new login when maximum sessions reached
						.expiredUrl("/login?expired")

				);


		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}
}
