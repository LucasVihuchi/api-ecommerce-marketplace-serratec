package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping
	public ResponseEntity<PedidoDTO> realizarPedido(@Validated(GruposValidacao.ValidadorPost.class) @RequestBody PedidoDTO pedidoDTO){
		Pedido pedido = pedidoDTO.converterParaPedido();
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/v1/usuarios/{id}/compras")
				.buildAndExpand(pedido.getId())
				.toUri();

		pedido = pedidoService.inserirPedido(pedido);
		return ResponseEntity.created(uri).body(new PedidoDTO(pedido));
	}

	@PutMapping("{id}")
	public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable Long id,
													 @Validated(GruposValidacao.ValidadorPut.class) @RequestBody PedidoDTO pedidoDTO) {

		Pedido pedido = pedidoDTO.converterParaPedido();
		pedido = pedidoService.atualizarPedido(id, pedido);
		return ResponseEntity.ok().body(new PedidoDTO(pedido));
	}

	@PutMapping("{id}/finalizar")
	public ResponseEntity<PedidoDTO> finalizarPedido(@PathVariable Long id) {
		Pedido pedido = pedidoService.finalizarPedido(id);
		return ResponseEntity.ok().body(new PedidoDTO(pedido));
	}
}
