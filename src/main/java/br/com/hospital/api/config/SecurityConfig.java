
package br.com.hospital.api.config;

import br.com.hospital.api.service.UsuarioDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Arquivos públicos
                        .requestMatchers(
                                "/assets/**",
                                "/login",
                                "/login.html",
                                "/csrf",
                                "/error"
                        ).permitAll()

                        .requestMatchers("/", "/index.html").authenticated()

                        // Administrador: acesso completo
                        .requestMatchers("/usuarios/**", "/grupos/**")
                        .hasRole("ADMINISTRADOR")

                        // Pacientes e telefones
                        .requestMatchers(HttpMethod.GET,
                                "/pacientes/**", "/telefones/**")
                        .hasAnyRole("ADMINISTRADOR", "MEDICO",
                                "ENFERMEIRO", "RECEPCAO")

                        .requestMatchers(HttpMethod.POST,
                                "/pacientes/**", "/telefones/**")
                        .hasAnyRole("ADMINISTRADOR", "RECEPCAO")

                        .requestMatchers(HttpMethod.PUT,
                                "/pacientes/**", "/telefones/**")
                        .hasAnyRole("ADMINISTRADOR", "RECEPCAO")

                        // Agendamentos
                        .requestMatchers(HttpMethod.GET, "/agendamentos/**")
                        .hasAnyRole("ADMINISTRADOR", "MEDICO",
                                "ENFERMEIRO", "RECEPCAO")

                        .requestMatchers(HttpMethod.POST, "/agendamentos/**")
                        .hasAnyRole("ADMINISTRADOR", "RECEPCAO")

                        .requestMatchers(HttpMethod.PUT, "/agendamentos/**")
                        .hasAnyRole("ADMINISTRADOR", "RECEPCAO")

                        // Informações de profissionais
                        .requestMatchers(HttpMethod.GET,
                                "/profissionais/**", "/medicos/**",
                                "/especialidades/**")
                        .hasAnyRole("ADMINISTRADOR", "MEDICO",
                                "ENFERMEIRO", "RECEPCAO")

                        // Informações clínicas: leitura
                        .requestMatchers(HttpMethod.GET,
                                "/atendimentos/**", "/prontuarios/**",
                                "/exames/**", "/prescricoes/**",
                                "/itens-prescricao/**", "/medicamentos/**")
                        .hasAnyRole("ADMINISTRADOR", "MEDICO",
                                "ENFERMEIRO")

                        // Informações clínicas: cadastro e edição
                        .requestMatchers(HttpMethod.POST,
                                "/atendimentos/**", "/prontuarios/**",
                                "/exames/**", "/prescricoes/**",
                                "/itens-prescricao/**")
                        .hasAnyRole("ADMINISTRADOR", "MEDICO")

                        .requestMatchers(HttpMethod.PUT,
                                "/atendimentos/**", "/prontuarios/**",
                                "/exames/**", "/prescricoes/**",
                                "/itens-prescricao/**")
                        .hasAnyRole("ADMINISTRADOR", "MEDICO")

                        // Relatórios
                        .requestMatchers(HttpMethod.GET, "/relatorios/**")
                        .hasAnyRole("ADMINISTRADOR", "MEDICO",
                                "ENFERMEIRO", "RECEPCAO")

                        // Token CSRF também disponível antes do login

                        //Informações do usuário autenticado
                        .requestMatchers("/auth/me").authenticated()

                        // Outras operações: somente administrador
                        .anyRequest().hasRole("ADMINISTRADOR")
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UsuarioDetailsService usuarioDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(usuarioDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}
