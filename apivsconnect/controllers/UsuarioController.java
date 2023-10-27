package com.senai.apivsconnect.controllers;

import com.senai.apivsconnect.dtos.UsuarioDTO;
import com.senai.apivsconnect.models.UsuarioModel;
import com.senai.apivsconnect.repositories.UsuarioRepository;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.email()) != null) {
            //Não pode cadastrar
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse e-mail já está cadastrado");
        }
        UsuarioModel usuario = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @PutMapping("/{idUsuario}") //Alterar dados
    public ResponseEntity<Object> editarUsuario(@PathVariable(value = "idUsuario") UUID id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Optional<UsuarioModel> usuarioBuscado = usuarioRepository.findById(id);

        if(usuarioBuscado.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        UsuarioModel usuario = usuarioBuscado.get();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @DeleteMapping("/{idUsuario}") //Deletar usuário
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "idUsuario") UUID id){
        Optional<UsuarioModel> usuarioBuscado = usuarioRepository.findById(id);

        if(usuarioBuscado.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        usuarioRepository.delete(usuarioBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso!");
    }

}

