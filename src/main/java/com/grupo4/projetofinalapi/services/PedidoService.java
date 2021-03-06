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

/** Classe service realizar a interface entre os controllers e o repository de pedido
 */
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

	/** Método para inserir um novo pedido no banco de dados
	 *
	 * @param pedido pedido a ser inserido
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 * @return Pedido que foi inserido no banco de dados
	 */
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

		if(comprador.getId().equals(vendedor.getId())) {
			throw new PedidoInconsistenteException("Comprador e vendedor não podem ser a mesma pessoa");
		}

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

	/** Método para atualizar um pedido no banco de dados
	 *
	 * @param id id do pedido a ser atualizado
	 * @param pedido pedido com os novos dados do pedido
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 * @return Pedido com os dados atualizados no banco de dados
	 */
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

		if(pedido.getListaItemPedido() != null) {
			if(pedido.getListaItemPedido().size() == 0) {
				throw new PedidoInconsistenteException("Lista de itens do pedido não pode ser declarada vazia");
			}
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

	/** Método para finalizar um pedido
	 *
	 * @param id id do pedido a ser finalizado
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 * @return Pedido que foi finalizado
	 */
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

	/** Método para atualizar os preços dos produtos no List de ItemPedido
	 *
	 * @param listaItemPedido List de ItemPedido a ser atualizado
	 * @return List de ItemPedido com os dados atualizados
	 */
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

	/** Método para atualizar o estoque de produtos no banco de dados
	 *
	 * @param listaItemPedido List de ItemPedido com os produtos a serem subtraídos
	 */
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

	/** Método para inserir o id do pedido em uma lista de ItemPedido
	 *
	 * @param id id do pedido
	 * @param pedido pedido que terá sua lista de ItemPedido atualizada
	 * @return List de ItemPedido atualizado
	 */
	public List<ItemPedido> insereIdPedidoListaItemPedidos(Long id, Pedido pedido){
		for (ItemPedido itemPedidoAtual : pedido.getListaItemPedido()) {
			itemPedidoAtual.setPedido(pedido);
			itemPedidoAtual.getPedido().setId(id);
		}
		return pedido.getListaItemPedido();
	}
}
