package com.grupo4.projetofinalapi.config;

import com.grupo4.projetofinalapi.services.CustomizadoUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Classe para configuração da autenticação e autorização da API
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomizadoUserDetailService customizadoUserDetailsService;

    /** Método para configurar o método de autenticação da API
     *
     * @param auth objeto construtor para definição de credenciais de acesso
     * @throws Exception caso ocorra um problema na construção do usuário
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customizadoUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /** Método para configurar a segurança da API, definindo níveis de permissão de acesso, proteção contra CRSF e prevenção contra salvamento de seção.
     * O método aplica o conceito de blacklist para a definição dos níveis de acesso
     *
     * @param http objeto que permite a configuração da segurança para as requisições HTTP.
     * @throws Exception caso ocorra um problema no processo de criação das configurações
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/*/categorias").authenticated()
                .antMatchers(HttpMethod.POST, "/api/*/produtos").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/*/usuarios/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/*/usuarios/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/*/usuarios/**").authenticated()
                .antMatchers("/api/*/pedidos").authenticated()
                .antMatchers("/api/*/pedidos/**").authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /** Método para definir o estilo de criptografia que será utilizada nas senhas durante o processo de autenticação.
     *
     * @return Objeto do tipo BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
