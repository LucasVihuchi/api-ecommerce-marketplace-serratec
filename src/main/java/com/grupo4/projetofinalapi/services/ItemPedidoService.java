package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.ItemPedido;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.IdNaoFornecidoException;
import com.grupo4.projetofinalapi.exceptions.PedidoInconsistenteException;
import com.grupo4.projetofinalapi.exceptions.ProdutoInexistenteException;
import com.grupo4.projetofinalapi.repositories.ItemPedidoRepository;
import com.grupo4.projetofinalapi.repositories.ProdutoRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/** Classe service para realizar a interface entre os controllers e o repository de ItemPedido
 */
@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /** Método para atualizar o List de ItemPedido de um pedido pelo id
     *
     * @param id id do pedido que contém a lista a ser atualizada
     * @param vendedor vendedor associado ao pedido
     * @param listaItemPedido List de ItemPedido com os novos dados
     * @return List de ItemPedido atualizado
     */
    public List<ItemPedido> atualizarListaItemPedido(Long id, Usuario vendedor, List<ItemPedido> listaItemPedido) {
        List<ItemPedido> listaItemPedidoBD = itemPedidoRepository.findAllByPedidoId(id);
        verificaListaItemPedido(vendedor, listaItemPedido);
        loopBD: for(ItemPedido itemPedidoBD : listaItemPedidoBD) {
            Produto produtoBD = (Produto) Hibernate.unproxy(itemPedidoBD.getProduto());
            for(int indice = 0; indice < listaItemPedido.size(); indice++) {
                Produto produto = (Produto) Hibernate.unproxy(listaItemPedido.get(indice).getProduto());
                if(produto.getId().equals(produtoBD.getId())) {
                    if(listaItemPedido.get(indice).getQuantidade() <= 0) {
                        continue loopBD;
                    }
                    itemPedidoBD.setQuantidade(listaItemPedido.get(indice).getQuantidade());
                    listaItemPedido.set(indice, itemPedidoBD);
                    continue loopBD;
                }
            }
            deletarItemPedido(itemPedidoBD);
        }
        return itemPedidoRepository.saveAll(listaItemPedido);
    }

    /** Método para deletar um ItemPedido do banco de dados
     *
     * @param itemPedido ItemPedido a ser deletado
     */
    @Transactional
    public void deletarItemPedido(ItemPedido itemPedido) {
        ItemPedido itemPedidoBD = itemPedidoRepository.findById(itemPedido.getId()).get();
        itemPedidoRepository.deletaItemPedidoById(itemPedido.getId());
    }

    /** Método para retornar a lista de ItemPedido de um pedido pelo seu id
     *
     * @param id id do pedido
     * @return List de ItemPedido do pedido
     */
    public List<ItemPedido> retornarListaItemPedidoPorPedidoId(Long id) {
        return itemPedidoRepository.findAllByPedidoId(id);
    }

    /** Método para verificar a validade de uma lista de ItemPedido
     *
     * @param vendedor vendedor ao qual o pedido está associado
     * @param listaItemPedido List de ItemPedido verificado
     */
    public void verificaListaItemPedido(Usuario vendedor, List<ItemPedido> listaItemPedido) {
        List<Long> indices = new ArrayList<>();
        for(ItemPedido itemPedidoAtual : listaItemPedido) {
            if(itemPedidoAtual.getProduto() == null) {
                throw new PedidoInconsistenteException("Produto deve ser fornecido para cada item da lista");
            }

            Long idProduto = itemPedidoAtual.getProduto().getId();

            if(idProduto == null) {
                throw new IdNaoFornecidoException("Id do produto não foi fornecido");
            }

            if(indices.contains(idProduto)) {
                throw new PedidoInconsistenteException("Produto duplicados");
            }
            indices.add(idProduto);

            if(itemPedidoAtual.getQuantidade() <= 0) {
                throw new PedidoInconsistenteException("Quantidade de produtos inválida");
            }

            Produto produto = produtoRepository.findById(idProduto)
                    .orElseThrow(() -> new ProdutoInexistenteException("Produto associado ao id " + idProduto + " não existe"));

            if(!produto.getVendedor().getId().equals(vendedor.getId())) {
                throw new PedidoInconsistenteException("Produto associado ao id " + idProduto + " não pertence ao vendedor de id " + vendedor.getId());
            }
            if(itemPedidoAtual.getQuantidade() > produto.getQtdEstoque()) {
                throw new PedidoInconsistenteException("Produto associado ao id " + idProduto + " não possui estoque suficiente. Tente mais tarde");
            }
        }
    }
}
