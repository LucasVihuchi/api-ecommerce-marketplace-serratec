package com.grupo4.projetofinalapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/** Classe para configuração da documentação com Swagger
 */
@Configuration
public class SpringFoxConfig {

    /** Método para configurar o Swagger
     *
     * @return Docket que configura o Swagger, seus endpoints e models que serão expostos na documentação
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.grupo4.projetofinalapi.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    /** Método para compor o cabeçalho da página do Swagger
     *
     * @return ApiInfo com informações que compõem o cabeçalho
     */
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("API Grupo 4 - Projeto final")
                .description("Api desenvolvida pelo grupo 4 como projeto final para a disciplina 'Desenvolvimento de API Restful' do programa de Residência Serratec 2021.2")
                .license("General Public License Version 3")
                .licenseUrl("https://www.gnu.org/licenses/gpl-3.0.pt-br.html")
                .version("1.0.0")
                .contact(new Contact("Grupo 4", "https://www.serratec.org.br", "grupo4.serratec@gmail.com"))
                .build();

        return apiInfo;
    }
}
