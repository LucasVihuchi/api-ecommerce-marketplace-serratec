package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.config.MailConfig;
import com.grupo4.projetofinalapi.entities.ItemPedido;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.StatusPedido;
import com.grupo4.projetofinalapi.exceptions.*;
import com.grupo4.projetofinalapi.repositories.PedidoRepository;
import com.grupo4.projetofinalapi.repositories.ProdutoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private MailConfig mailConfig;

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
	@Transactional
	public Pedido finalizarPedido(Long id) {
		Pedido pedidoBD = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoInexistenteException("Pedido associado ao id " + id + " não existe"));

		if(pedidoBD.getStatusPedido().equals(StatusPedido.FINALIZADO)) {
			throw new PedidoJaFinalizadoException("Pedido associado ao id " + id + " já foi finalizado");
		}
		atualizaQtdEstoque(pedidoBD.getListaItemPedido());
		pedidoBD.setStatusPedido(StatusPedido.FINALIZADO);

		String assunto = "Marketplace Grupo 4 - Pedido " + pedidoBD.getId() + " finalizado";
		String conteudo = pedidoBD.gerarTemplateEmail();
		mailConfig.sendMail(pedidoBD.getComprador().getEmail(), assunto, conteudo);

		return pedidoBD;
	}


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

	@Transactional
	public void atualizaQtdEstoque(List<ItemPedido> listaItemPedido) {
		for (ItemPedido itemPedidoAtual : listaItemPedido) {
			Produto produto = produtoRepository.findById(itemPedidoAtual.getProduto().getId())
					.orElseThrow(() -> new ProdutoInexistenteException("Produto associado ao id " + itemPedidoAtual.getProduto().getId() + " não existe"));
			if (itemPedidoAtual.getQuantidade() > produto.getQtdEstoque()) {
				throw new PedidoInconsistenteException("Produto associado ao id " + produto.getId() + " não possui estoque suficiente. Tente mais tarde");
			}
			produto.setQtdEstoque(produto.getQtdEstoque() - itemPedidoAtual.getQuantidade());
		}
	}
}
