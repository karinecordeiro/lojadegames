package org.generation.lojadegames.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.lojadegames.model.Usuario;
import org.generation.lojadegames.model.UsuarioLogin;
import org.generation.lojadegames.repository.UsuarioRepository;
import org.generation.lojadegames.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins= "*", allowedHeaders= "*")

public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById (@PathVariable Long id){
		return usuarioRepository.findById(id).map(resp ->ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());			
	}
	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<List<Usuario>> getByUsuario(@PathVariable String usuario){
		return ResponseEntity.ok(usuarioRepository.findAllByUsuarioContainingIgnoreCase(usuario));
				
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity <Usuario> cadastrarUsuario (@Valid @RequestBody Usuario usuario){
		return usuarioService.cadastrarUsuario(usuario).map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> logarUsuario (@RequestBody Optional <UsuarioLogin> usuarioLogin){
		return usuarioService.autenticarUsuario(usuarioLogin).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	@PutMapping("/atualizar")  
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario){
        return usuarioService.atualizarUsuario(usuario).map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
        		.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
	
}
