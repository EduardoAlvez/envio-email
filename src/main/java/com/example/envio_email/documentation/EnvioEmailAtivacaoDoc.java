package com.example.envio_email.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Envia email de ativação de conta",
        description = "Envia um email de ativação com template HTML responsivo")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email enviado com sucesso",
                content = @Content(examples = @ExampleObject(
                        value = "{\"status\":\"SUCCESS\",\"message\":\"Email enviado com sucesso!\"}"
                ))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
})
public @interface EnvioEmailAtivacaoDoc {
}