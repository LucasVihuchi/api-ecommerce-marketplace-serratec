package com.grupo4.projetofinalapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.services.PedidoService;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("compras/{id}")
	public List<PedidoDTO> obterListasPedidoPorComprador(Long id){
		List<PedidoDTO> listaPedidosDTO = PedidoDTO.converterParaPedidosDTO(pedidoService.obterListasPedidoPorComprador(id));
		return listaPedidosDTO;
	}
	
	// TODO criar a funcao para os pedidos de venda
}
