package efub.assignment.community.global.config;

import efub.assignment.community.global.jwt.TokenProvider;
import efub.assignment.community.global.oauth.OAuth2AuthenticationSuccessHandler;
import efub.assignment.community.global.jwt.JwtAuthenticationFilter;
import efub.assignment.community.global.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private static final String[] AUTH_WHITELIST = {
        "/auth/token"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> {
                request.requestMatchers(AUTH_WHITELIST).permitAll();
                request.requestMatchers(HttpMethod.GET).permitAll();
                request.anyRequest().authenticated();
            })
            .sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                .successHandler(oAuth2AuthenticationSuccessHandler))
            .build();
    }

}
