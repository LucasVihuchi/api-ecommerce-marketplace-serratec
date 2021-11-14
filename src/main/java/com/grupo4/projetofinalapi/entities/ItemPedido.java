package com.grupo4.projetofinalapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.groups.GruposValidacao.ValidadorPost;
import com.grupo4.projetofinalapi.groups.GruposValidacao.ValidadorPut;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Table (uniqueConstraints = {
		@UniqueConstraint (name = "ItemPedidoUnico", columnNames = {"cod_produto", "cod_pedido"})
})
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_item_pedido", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@NotNull(message = "Quantidade não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
	@Positive(message = "Quantidade não pode ser negativa ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false)
	private int quantidade;
	
	@NotBlank(message = "Preço não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Positive(message = "Preço não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	private Double precoUnitario;
	
	@NotNull(message = "Produto não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_produto", nullable = false, columnDefinition = "int4")
	@JsonIgnoreProperties({"vendedor"})
	private Produto produto;
		
//	@NotNull(message = "Pedido não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn (name = "cod_pedido", nullable = false, columnDefinition = "int4")
	private Pedido pedido;

	public ItemPedido() {
		super();
	}

	public ItemPedido(Long id, int quantidade, Double precoUnitario, Produto produto, Pedido pedido) {
		this.id = id;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
		this.produto = produto;
		this.pedido = pedido;
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, pedido, precoUnitario, produto, quantidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		return Objects.equals(id, other.id) && Objects.equals(pedido, other.pedido)
				&& Objects.equals(precoUnitario, other.precoUnitario) && Objects.equals(produto, other.produto)
				&& quantidade == other.quantidade;
	}
	
	
}
