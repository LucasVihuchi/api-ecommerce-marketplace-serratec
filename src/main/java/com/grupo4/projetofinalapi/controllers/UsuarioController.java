package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.dto.UsuarioDTO;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.PedidoService;
import com.grupo4.projetofinalapi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PedidoService pedidoService;
	
	
	@PostMapping
	public ResponseEntity<Object> criarUsuario(@Validated(GruposValidacao.ValidadorPost.class) @RequestBody UsuarioDTO usuarioDTO){
	
		Usuario usuario = usuarioDTO.converterParaUsuario();
		usuario = usuarioService.inserirUsuario(usuario);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
	}
	
	@DeleteMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	public ResponseEntity<?> deletarUsuario(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
		usuarioService.deletarUsuario(usuarioAutenticado);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@Validated(GruposValidacao.ValidadorPut.class) @RequestBody UsuarioDTO usuarioDTO, @AuthenticationPrincipal UserDetails usuarioAutenticado) {
		Usuario usuario = usuarioDTO.converterParaUsuario();
		usuario = usuarioService.atualizarUsuario(usuario, usuarioAutenticado);
		
		return ResponseEntity.ok().body(new UsuarioDTO(usuario));
	}

	@GetMapping("compras")
	@PreAuthorize("hasRole('ROLE_usuario')")
	public ResponseEntity<List<PedidoDTO>> obterListaPedidosPorComprador(@AuthenticationPrincipal UserDetails usuarioAutenticado){
		List<PedidoDTO> listaPedidosDTO = PedidoDTO.converterParaPedidosDTO(usuarioService.obterListaPedidosPorComprador(usuarioAutenticado));
		return ResponseEntity.ok().body(listaPedidosDTO);
	}

	@GetMapping("vendas")
	@PreAuthorize("hasRole('ROLE_usuario')")
	public ResponseEntity<List<PedidoDTO>> obterListaPedidosPorVendedor(@AuthenticationPrincipal UserDetails usuarioAutenticado){
		List<PedidoDTO> listaPedidosDTO = PedidoDTO.converterParaPedidosDTO(usuarioService.obterListaPedidosPorVendedor(usuarioAutenticado));
		return ResponseEntity.ok().body(listaPedidosDTO);
	}
}
