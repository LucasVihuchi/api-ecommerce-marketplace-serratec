package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.ItemPedido;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.StatusPedido;
import com.grupo4.projetofinalapi.exceptions.PedidoInconsistenteException;
import com.grupo4.projetofinalapi.exceptions.PedidoInexistenteException;
import com.grupo4.projetofinalapi.exceptions.ProdutoInexistenteException;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.PedidoRepository;
import com.grupo4.projetofinalapi.repositories.ProdutoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
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
	
	public Pedido inserirPedido(Pedido pedido) {
		Usuario comprador = usuarioRepository.findById(pedido.getComprador().getId())
				.orElseThrow(() -> new UsuarioInexistenteException("Comprador associado ao id " + pedido.getComprador().getId() + " não existe"));
	
		Usuario vendedor = usuarioRepository.findById(pedido.getVendedor().getId())
				.orElseThrow(() -> new UsuarioInexistenteException("Vendedor associado ao id " + pedido.getVendedor().getId() + " não existe"));

		if(pedido.getListaItemPedido().size() == 0) {
			throw new PedidoInconsistenteException("Pedido deve conter produtos");
		}
		
		verificaListaItemPedido(vendedor, pedido.getListaItemPedido());
		pedido.setListaItemPedido(atualizaPrecosItemPedido(pedido.getListaItemPedido()));
		
		pedido.setStatusPedido(StatusPedido.NAO_FINALIZADO);
		pedido.setDataPedido(LocalDateTime.now());
		
		return pedidoRepository.saveAndFlush(pedido);
		
	}
	
	@Transactional
	public Pedido atualizarPedido(Long id, Pedido pedido) {
		Pedido pedidoBD = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoInexistenteException("Pedido associado ao id " + id + " não existe"));
		
		if(pedido.getFretePedido() != null) {
			pedidoBD.setFretePedido(pedido.getFretePedido());
		}
		if(pedido.getListaItemPedido().size() != 0) {
			verificaListaItemPedido(pedidoBD.getVendedor(), pedido.getListaItemPedido());
			pedidoBD.setListaItemPedido(atualizaPrecosItemPedido(pedido.getListaItemPedido()));
		}
				
		return pedidoBD;
	}
	
	//TODO lembrar de alterar o estoque quando finalizar pedido e salvar quantidade e preço.
	
	public void verificaListaItemPedido(Usuario vendedor, List<ItemPedido> listaItemPedido) {
		for(ItemPedido itemPedidoAtual : listaItemPedido) {
			Produto produto = produtoRepository.findById(itemPedidoAtual.getProduto().getId())
				.orElseThrow(() -> new ProdutoInexistenteException("Produto associado ao id " + itemPedidoAtual.getProduto().getId() + " não existe"));
			if(!produto.getVendedor().getId().equals(vendedor.getId())) {
				throw new PedidoInconsistenteException("Produto associado ao id " + produto.getId() + " não pertence ao vendedor de id " + vendedor.getId());
			}
			if(itemPedidoAtual.getQuantidade() > produto.getQtdEstoque()) {
				throw new PedidoInconsistenteException("Produto associado ao id " + produto.getId() + " não possui estoque suficiente. Tente mais tarde");
			}
		}
	}
	
	public List<ItemPedido> atualizaPrecosItemPedido(List<ItemPedido> listaItemPedido){
		int indice;
		for(indice = 0; indice < listaItemPedido.size(); indice++) {
			long id = listaItemPedido.get(indice).getProduto().getId();
			Produto produto = produtoRepository.findById(id)
					.orElseThrow(() -> new ProdutoInexistenteException("Produto associado ao id " + id + " não existe"));
			listaItemPedido.get(indice).setPrecoUnitario(produto.getPrecoUnitario());
			listaItemPedido.set(indice, listaItemPedido.get(indice));
		}
		return listaItemPedido;
	}
	
}
