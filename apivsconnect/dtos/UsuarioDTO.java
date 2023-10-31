package com.senai.apivsconnect.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public record UsuarioDTO(
        @NotBlank String nome,
        @NotBlank @Email(message = "O e-mail deve estar em um formato válido") String email,
        @NotBlank String senha,
        String endereco,
        String cep,
        String tipo_usuario,
        MultipartFile imagem
) {

}
