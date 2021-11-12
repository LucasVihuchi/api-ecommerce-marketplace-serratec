package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
//	public List<Pedido> obterListaPedidosPorComprador(Long id){
//		Usuario usuarioBD = usuarioRepository.findById(id)
//				.orElseThrow(() -> new UsuarioInexistenteException("Usuario não existe"));
//		return usuarioBD.getListaPedidosFeitos();
//	}
//
//	public List<Pedido> obterListaPedidosPorVendedor(Long id){
//		Usuario usuarioBD = usuarioRepository.findById(id)
//				.orElseThrow(() -> new UsuarioInexistenteException("Usuario não existe"));
//		return usuarioBD.getListaPedidosRecebidos();
//	}

	// TODO Remover a classe se não for usada para nada
	
}
