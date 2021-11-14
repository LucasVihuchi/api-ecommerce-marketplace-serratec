package com.grupo4.projetofinalapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo4.projetofinalapi.entities.ItemPedido;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.groups.GruposValidacao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemPedidoDTO {

    private Long id;

    @NotNull(message = "Quantidade não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
    @Positive(message = "Quantidade não pode ser negativa ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
    private int quantidade;

    @NotBlank(message = "Preço não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
    @Positive(message = "Preço não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
    private Double precoUnitario;

    @NotNull(message = "Produto não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
    @JsonIgnoreProperties({"vendedor"})
    private ProdutoDTO produto;

//    @NotNull(message = "Pedido não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
//    private PedidoDTO pedido;

    public ItemPedidoDTO() {
    }

    public ItemPedidoDTO(Long id,
                         int quantidade,
                         Double precoUnitario,
                         ProdutoDTO produto,
                         PedidoDTO pedido) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.produto = produto;
//        this.pedido = pedido;
    }

    public ItemPedidoDTO(ItemPedido itemPedido) {
        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
        this.id = itemPedido.getId();
        this.quantidade = itemPedido.getQuantidade();
        this.precoUnitario = itemPedido.getPrecoUnitario();
        this.produto = new ProdutoDTO(itemPedido.getProduto());
//        if(itemPedido.getPedido() != null) {
//            this.pedido = new PedidoDTO(itemPedido.getPedido());
//        }

    }

    public ItemPedido converterParaItemPedido() {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(this.id);
        itemPedido.setQuantidade(this.quantidade);
        itemPedido.setPrecoUnitario(this.precoUnitario);
        itemPedido.setProduto(this.produto.converterParaProduto());
//        if(this.pedido != null) {
//            itemPedido.setPedido(this.pedido.converterParaPedido());
//        }


        return itemPedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

//    public PedidoDTO getPedido() {
//        return pedido;
//    }
//
//    public void setPedido(PedidoDTO pedido) {
//        this.pedido = pedido;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedidoDTO that = (ItemPedidoDTO) o;
        return quantidade == that.quantidade && Objects.equals(id, that.id) && Objects.equals(precoUnitario, that.precoUnitario) && Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidade, precoUnitario, produto);
    }

    public static List<ItemPedidoDTO> converterParaListaItemPedidoDTO(List<ItemPedido> listaItemPedido) {
        List<ItemPedidoDTO> listaItemPedidoDTO = new ArrayList<>();
        for(ItemPedido itemPedido : listaItemPedido) {
            listaItemPedidoDTO.add(new ItemPedidoDTO(itemPedido));
        }

        return listaItemPedidoDTO;
    }

    public static List<ItemPedido> converterParaListaItemPedido(List<ItemPedidoDTO> listaItemPedidoDTO) {
        List<ItemPedido> listaItemPedido = new ArrayList<>();
        for(ItemPedidoDTO itemPedidoDTO : listaItemPedidoDTO) {
            listaItemPedido.add(itemPedidoDTO.converterParaItemPedido());
        }

        return listaItemPedido;
    }
}
