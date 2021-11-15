package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.ItemPedidoService;
import com.grupo4.projetofinalapi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	public ResponseEntity<PedidoDTO> realizarPedido(@Validated(GruposValidacao.ValidadorPost.class) @RequestBody PedidoDTO pedidoDTO, @AuthenticationPrincipal UserDetails usuarioAutenticado){
		Pedido pedido = pedidoDTO.converterParaPedido();
		pedido = pedidoService.inserirPedido(pedido, usuarioAutenticado);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/v1/pedidos/{id}")
				.buildAndExpand(pedido.getId())
				.toUri();

		return ResponseEntity.created(uri).body(new PedidoDTO(pedido));
	}

	@PutMapping("{id}")
	@PreAuthorize("hasRole('ROLE_usuario')")
	public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable Long id,
													 @Validated(GruposValidacao.ValidadorPut.class) @RequestBody PedidoDTO pedidoDTO,
													 @AuthenticationPrincipal UserDetails usuarioAutenticado) {

		Pedido pedido = pedidoDTO.converterParaPedido();
		pedido = pedidoService.atualizarPedido(id, pedido, usuarioAutenticado);
		pedido.setListaItemPedido(itemPedidoService.retornarListaItemPedidoPorPedidoId(id));

		return ResponseEntity.ok().body(new PedidoDTO(pedido));
	}

	@PutMapping("{id}/finalizar")
	@PreAuthorize("hasRole('ROLE_usuario')")
	public ResponseEntity<PedidoDTO> finalizarPedido(@PathVariable Long id, @AuthenticationPrincipal UserDetails usuarioAutenticado) {
		Pedido pedido = pedidoService.finalizarPedido(id, usuarioAutenticado);
		return ResponseEntity.ok().body(new PedidoDTO(pedido));
	}
}
