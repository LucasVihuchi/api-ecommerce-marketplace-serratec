package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.PedidoService;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping
	public ResponseEntity<Pedido> realizarPedido(@Validated(GruposValidacao.ValidadorPost.class) @RequestBody PedidoDTO pedidoDTO){
		Pedido pedido = pedidoDTO.converterParaPedido();
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/v1/usuarios/{id}/compras")
				.buildAndExpand(pedido.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(pedidoService.inserirPedido(pedido));
		
	}
}
