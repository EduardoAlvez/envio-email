package com.example.envio_email.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Envio de Email - Ativação de Conta")
                        .version("1.0.0")
                        .description("""
                    # 📧 Microserviço de Envio de Emails
                    
                    ## Sobre
                    Este microserviço é responsável por enviar emails de ativação de conta
                    com templates HTML personalizados usando Thymeleaf.
                    
                    ## Funcionalidades
                    * Envio de email de ativação com token
                    * Templates responsivos e estilizados
                    * Validação completa dos dados de entrada
                    * Tratamento de erros robusto
                    
                    ## Como usar
                    1. Faça uma requisição POST para `/api/email/v1/enviar-verificacao`
                    2. Envie os dados do usuário e link de ativação
                    3. O email será enviado para o destinatário
                    
                    ## Códigos de resposta
                    * `200` - Email enviado com sucesso
                    * `400` - Dados inválidos (validação falhou)
                    * `500` - Erro interno ao enviar email
                    """)
                        .contact(new Contact()
                                .name("Eduardo")
                                .email("eduardo@email.com")
                                .url("https://github.com/EduardoAlvez"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local")
                ))
                .tags(List.of(
                        new Tag().name("Email").description("Endpoints para envio de emails"),
                        new Tag().name("Usuário").description("Endpoints para registro de usuários"),
                        new Tag().name("Validação").description("Endpoints de validação de token")
                ));
    }
}
