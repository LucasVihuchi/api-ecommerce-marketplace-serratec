package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoService;
	
//	@GetMapping("compras/{id}")
//	public List<PedidoDTO> obterListasPedidoPorComprador(Long id){
//		List<PedidoDTO> listaPedidosDTO = PedidoDTO.converterParaPedidosDTO(pedidoService.obterListaPedidosPorComprador(id));
//		return listaPedidosDTO;
//	}
	
	// TODO Remover a classe se não for usada para nada
}
