package com.city.list.config;

import com.city.list.enums.UserRole;
import com.city.list.repository.specification.IUserRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static java.lang.String.format;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final IUserRepository userRepo;

  @Value("${jwt.public.key}")
  private RSAPublicKey rsaPublicKey;

  @Value("${jwt.private.key}")
  private RSAPrivateKey rsaPrivateKey;

  @Value("${springdoc.api-docs.path}")
  private String restApiDocPath;

  @Value("${springdoc.swagger-ui.path}")
  private String swaggerPath;

  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(
            username ->
                userRepo
                    .getByUsername(username)
                    .orElseThrow(
                        () ->
                            new UsernameNotFoundException(format("User: %s, not found", username))))
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
        .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Enable CORS and disable CSRF
    http.cors().and().csrf().disable();

    // Set session management to stateless
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Set unauthorized requests exception handler
    http.exceptionHandling(
        (exceptions) ->
            exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));

    // Set permissions on endpoints
    http.authorizeRequests()
            // Swagger endpoints must be publicly accessible
            .antMatchers("/")
            .permitAll()
            .antMatchers(format("%s/**", restApiDocPath))
            .permitAll()
            .antMatchers(format("%s/**", swaggerPath))
            .permitAll()
            // Other endpoints
            .antMatchers("/public/**")
            .permitAll()
            .antMatchers(HttpMethod.POST, "/cities/**")
            .hasAnyRole(String.valueOf(UserRole.ADMIN))
            .antMatchers(HttpMethod.GET, "/cities/**")
            .permitAll()
            .antMatchers(HttpMethod.POST, "/users/**")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/health")
            .hasAnyRole(String.valueOf(UserRole.ADMIN))
            // Our private endpoints
            .anyRequest()
            .authenticated()
            // Set up oauth2 resource server
            .and()
            .httpBasic(Customizer.withDefaults())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

    return http.build();
  }

  // Used by JwtAuthenticationProvider to generate JWT tokens
  @Bean
  public JwtEncoder jwtEncoder() {
    var jwk = new RSAKey.Builder(this.rsaPublicKey).privateKey(this.rsaPrivateKey).build();
    var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  // Used by JwtAuthenticationProvider to decode and validate JWT tokens
  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(this.rsaPublicKey).build();
  }

  // Extract authorities from the roles claim
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

    var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  // Set password encoding schema
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Used by spring security if CORS is enabled.
  @Bean
  public CorsFilter corsFilter() {
    var source = new UrlBasedCorsConfigurationSource();
    var config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  // Expose authentication manager bean
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.debug(false)
            .ignoring()
            .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
  }

}
