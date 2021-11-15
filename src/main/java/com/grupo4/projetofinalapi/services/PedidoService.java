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
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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

	@Autowired
	private ItemPedidoService itemPedidoService;

	public Pedido inserirPedido(Pedido pedido, UserDetails usuarioAutenticado) {
		if(pedido.getVendedor().getId() == null) {
			throw new IdNaoFornecidoException("Id do vendedor não foi fornecido");
		}

		Usuario comprador = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Comprador associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));

		pedido.setComprador(comprador);

		Usuario vendedor = usuarioRepository.findById(pedido.getVendedor().getId())
				.orElseThrow(() -> new UsuarioInexistenteException("Vendedor associado ao id " + pedido.getVendedor().getId() + " não existe"));

		pedido.setVendedor(vendedor);

		if(pedido.getListaItemPedido().size() == 0) {
			throw new PedidoInconsistenteException("Pedido deve conter produtos");
		}
		
		itemPedidoService.verificaListaItemPedido(vendedor, pedido.getListaItemPedido());

		pedido.setListaItemPedido(insereIdPedidoListaItemPedidos(pedidoRepository.getNextValMySequence(), pedido));
		pedido.setListaItemPedido(atualizaPrecosItemPedido(pedido.getListaItemPedido()));
		
		pedido.setStatusPedido(StatusPedido.NAO_FINALIZADO);
		pedido.setDataPedido(LocalDateTime.now());

		return pedidoRepository.saveAndFlush(pedido);
		
	}

	@Transactional
	public Pedido atualizarPedido(Long id, Pedido pedido, UserDetails usuarioAutenticado) {
		Pedido pedidoBD = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoInexistenteException("Pedido associado ao id " + id + " não existe"));

		Usuario compradorBD = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));

		if(!pedidoBD.getComprador().getId().equals(compradorBD.getId())) {
			throw new PedidoInconsistenteException("Usuário autenticado não tem permissão para alterar pedidos de terceiros");
		}

		if(pedidoBD.getStatusPedido() == StatusPedido.FINALIZADO) {
			throw new PedidoJaFinalizadoException("Pedido associado ao id " + id + " já foi finalizado");
		}

		if(pedido.getFretePedido() != null) {
			pedidoBD.setFretePedido(pedido.getFretePedido());
		}
		if(pedido.getListaItemPedido() != null && pedido.getListaItemPedido().size() != 0) {
			itemPedidoService.verificaListaItemPedido(pedidoBD.getVendedor(), pedido.getListaItemPedido());
			pedido.setListaItemPedido(insereIdPedidoListaItemPedidos(pedidoBD.getId(), pedido));
			pedido.setListaItemPedido(atualizaPrecosItemPedido(pedido.getListaItemPedido()));
			itemPedidoService.atualizarListaItemPedido(id, pedidoBD.getVendedor(), pedido.getListaItemPedido());
		}
		// Resolver Put
		return pedidoBD;
	}

	@Transactional
	public Pedido finalizarPedido(Long id, UserDetails usuarioAutenticado) {
		Pedido pedidoBD = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoInexistenteException("Pedido associado ao id " + id + " não existe"));

		Usuario compradorBD = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));

		if(!pedidoBD.getComprador().getId().equals(compradorBD.getId())) {
			throw new PedidoInconsistenteException("Usuário autenticado não tem permissão para alterar pedidos de terceiros");
		}

		if(pedidoBD.getStatusPedido().equals(StatusPedido.FINALIZADO)) {
			throw new PedidoJaFinalizadoException("Pedido associado ao id " + id + " já foi finalizado");
		}

		atualizaQtdEstoque(pedidoBD.getListaItemPedido());
		pedidoBD.setStatusPedido(StatusPedido.FINALIZADO);

		String assunto = "Marketplace Grupo 4 - Pedido " + pedidoBD.getId() + " finalizado";
		String conteudo = pedidoBD.gerarTemplateEmail();
		try {
			mailConfig.sendMail(pedidoBD.getComprador().getEmail(), assunto, conteudo);
		} catch (MessagingException e) {
			throw new EmailNaoEnviadoException("Ocorreu um erro na criação do email de confirmação de pedido");
		}

		return pedidoBD;
	}

	public List<ItemPedido> atualizaPrecosItemPedido(List<ItemPedido> listaItemPedido){
		int indice;
		for(indice = 0; indice < listaItemPedido.size(); indice++) {
			long id = listaItemPedido.get(indice).getProduto().getId();
			Produto produto = produtoRepository.findById(id)
					.orElseThrow(() -> new ProdutoInexistenteException("Produto associado ao id " + id + " não existe"));
			produto = (Produto) Hibernate.unproxy(produto);
			listaItemPedido.get(indice).setPrecoUnitario(produto.getPrecoUnitario());
			listaItemPedido.set(indice, listaItemPedido.get(indice));
			listaItemPedido.get(indice).setProduto(produto);
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

	public List<ItemPedido> insereIdPedidoListaItemPedidos(Long id, Pedido pedido){
		for (ItemPedido itemPedidoAtual : pedido.getListaItemPedido()) {
			itemPedidoAtual.setPedido(pedido);
			itemPedidoAtual.getPedido().setId(id);
		}
		return pedido.getListaItemPedido();
	}
}
