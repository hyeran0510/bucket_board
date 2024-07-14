package cookie.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
    //인증되지않은 모든 페이지 요청 허락가능, 모든페이지 접근가능
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        // /bucket/** 경로에 대한 접근을 모두 허용합니다.
                        .requestMatchers(new AntPathRequestMatcher("/bucket/**")).permitAll()
                        // 모든 다른 경로에 대한 접근을 허용합니다.
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                // CSRF 보호를 /mariadb/** 경로에 대해서만 비활성화합니다.
                .csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/mariadb/**")))
                // X-Frame-Options 헤더를 SAMEORIGIN으로 설정하여 동일 출처의 페이지에서만 프레임에 포함될 수 있도록 합니다.
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // 커스텀 로그인 페이지를 설정하고, 로그인 성공 시 기본적으로 "/"로 리디렉션
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/"))
                // 로그아웃 설정을 정의
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));

        return http.build();
    }

    // 비밀번호 인코더 Bean을 생성합니다. BCryptPasswordEncoder를 사용합니다.
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager Bean을 생성합니다.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}