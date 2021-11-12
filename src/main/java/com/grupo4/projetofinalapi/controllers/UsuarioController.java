package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.dto.UsuarioDTO;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

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
	public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
		usuarioService.deletarUsuario(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioDTO.converterParaUsuario();
		usuario = usuarioService.atualizarUsuario(id, usuario);
		
		return ResponseEntity.ok().body(new UsuarioDTO(usuario));
	}

	@GetMapping("compras/{id}")
	public ResponseEntity<List<PedidoDTO>> obterListasPedidoPorComprador(Long id){
		List<PedidoDTO> listaPedidosDTO = PedidoDTO.converterParaPedidosDTO(usuarioService.obterListaPedidosPorComprador(id));
		return ResponseEntity.ok().body(listaPedidosDTO);
	}

	// TODO criar a funcao para os pedidos de venda
}
