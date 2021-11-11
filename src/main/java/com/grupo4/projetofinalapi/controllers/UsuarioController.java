package com.grupo4.projetofinalapi.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.grupo4.projetofinalapi.dto.UsuarioDTO;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.services.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
//	
// TODO terminar o post de usuario	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@PostMapping
	public ResponseEntity<Object> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){
	
		Usuario usuario = usuarioDTO.converterParaUsuario();
		usuario = usuarioService.inserirUsuario(usuario);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
	
	}
	
	@DeleteMapping("{id}")
	public void deletarUsuario(@PathVariable Long id) {
		usuarioService.deletarUsuario(id);
	}
	
	@PutMapping("{id}")
	public UsuarioDTO atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioDTO.converterParaUsuario();
		usuario = usuarioService.atualizarUsuario(id, usuario);
		
		return new UsuarioDTO(usuario);
	}
}
