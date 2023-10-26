package com.senai.apivsconnect.controllers;

import com.senai.apivsconnect.models.UsuarioModel;
import com.senai.apivsconnect.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController // indica para o framework que se trata de um controlador Rest, voltado para o desenvolvimento de aplicações web Restful e facilita que nós lidemos com requisições web (POST, GET, PUT, etc) pois une o Controller a um ResponseBody para todos métodos marcados pelo RequestMapping.
// fazer com que o Controle seja acessado, ele precisa de um caminho (URI), fazendo o mapeamento de requisição
@RequestMapping (value="/usuarios", produces = {"application/json"})
public class UsuarioController {
    @Autowired //Rolando uma injeção de dependencia, trazendo uma outra classe pra ser utilizada dentro da classe
    UsuarioRepository usuarioRepository;

    @GetMapping // método para listar todos os usuarios
    public ResponseEntity<List<UsuarioModel>> listarUsuarios() { // metodo responsável em receber a requisição com metodo get
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll()); // restorna uma responta de requisição, pega os usuário do repository
    }

    @GetMapping("/{idUsuario}") // metodo de mostrar apenas 1 usuário
    public ResponseEntity<Object> exibirUsuario(@PathVariable(value = "idUsuario") UUID id) {
        Optional<UsuarioModel> usuarioBuscado = usuarioRepository.findById(id);

        if (usuarioBuscado.isEmpty()) {
            // retornar usuário não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarioBuscado.get());

    }
}

