package com.grupo4.projetofinalapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.PedidoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;


@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository ;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Pedido> obterListasPedidoPorComprador(Long id){
		Usuario usuarioBD = usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioInexistenteException("Usuario não existe"));
		return usuarioBD.getListaPedidosFeitos();
	}
	
	public List<Pedido> obterListasPedidoPorVendedor(Long id){
		Usuario usuarioBD = usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioInexistenteException("Usuario não existe"));
		return usuarioBD.getListaPedidosRecebidos();
	}
	
}
