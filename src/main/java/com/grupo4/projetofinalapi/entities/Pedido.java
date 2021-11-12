package com.grupo4.projetofinalapi.entities;

import com.grupo4.projetofinalapi.enums.StatusPedido;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/*
 * Precisamos fazer o metodo post, put:
 * - Visualizar todos os pedidos.
 * - Criar um novo Pedido
 * - Editar um pedido com status de não finalizado.
 * - Finalizar um pedido, alterar seu status para finalizado.
 * - Ao finalizar o pedido enviar e-mail para o cliente informando data de envio, data de
entrega, produtos, quantidades e valor final do pedido.
 */

@Entity
@Table (uniqueConstraints = {
		@UniqueConstraint (name = "PedidoUnico", columnNames = {"cod_comprador", "cod_vendedor", "data_pedido"})
})
public class Pedido {
		
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "cod_pedido", nullable = false, columnDefinition = "serial")
	private Long id;

	@NotNull
	@PastOrPresent
	@DateTimeFormat (pattern = "dd/MM/yyyy")
	@Column (name = "data_pedido", nullable = false)
	private LocalDateTime dataPedido;
	
	@NotNull
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	private Double fretePedido;
	
	@Enumerated (EnumType.STRING)
	@Column (nullable = false)
	private StatusPedido statusPedido;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_comprador", nullable = false, columnDefinition = "int4")
	private Usuario comprador;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_vendedor", nullable = false, columnDefinition = "int4")
	private Usuario vendedor;
	
	@OneToMany (mappedBy = "pedido")
	private List<ItemPedido> listaItemPedido;


	public Pedido() {
	}

	public Pedido(Long id,
				  LocalDateTime dataPedido,
				  Double fretePedido,
				  StatusPedido statusPedido,
				  Usuario comprador,
				  Usuario vendedor,
				  List<ItemPedido> listaItemPedido) {
		this.id = id;
		this.dataPedido = dataPedido;
		this.fretePedido = fretePedido;
		this.statusPedido = statusPedido;
		this.comprador = comprador;
		this.vendedor = vendedor;
		this.listaItemPedido = listaItemPedido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Double getFretePedido() {
		return fretePedido;
	}

	public void setFretePedido(Double fretePedido) {
		this.fretePedido = fretePedido;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public void setComprador(Usuario comprador) {
		this.comprador = comprador;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public List<ItemPedido> getListaItemPedido() {
		return listaItemPedido;
	}

	public void setListaItemPedido(List<ItemPedido> listaItemPedido) {
		this.listaItemPedido = listaItemPedido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(comprador, dataPedido, fretePedido, id, listaItemPedido, statusPedido, vendedor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(comprador, other.comprador) && Objects.equals(dataPedido, other.dataPedido)
				&& Objects.equals(fretePedido, other.fretePedido) && Objects.equals(id, other.id)
				&& Objects.equals(listaItemPedido, other.listaItemPedido) && statusPedido == other.statusPedido
				&& Objects.equals(vendedor, other.vendedor);
	}
}
