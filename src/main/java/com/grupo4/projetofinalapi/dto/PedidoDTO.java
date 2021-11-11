package com.grupo4.projetofinalapi.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.grupo4.projetofinalapi.entities.ItemPedido;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.StatusPedido;

public class PedidoDTO {

	private Long id;
	
	
	@NotNull
	@PastOrPresent
	@DateTimeFormat (pattern = "dd/MM/yyyy")
	private LocalDateTime dataPedido;
	
	@NotNull
	private Double fretePedido;
	
	@Enumerated (EnumType.STRING)
	private StatusPedido statusPedido;
	
	private UsuarioDTO comprador;
	

	private UsuarioDTO vendedor;
	
	private List<ItemPedido> listaItemPedido;

	public PedidoDTO(Long id, @NotNull @PastOrPresent LocalDateTime dataPedido, @NotNull Double fretePedido,
			StatusPedido statusPedido, UsuarioDTO comprador, UsuarioDTO vendedor, List<ItemPedido> listaItemPedido) {
		this.id = id;
		this.dataPedido = dataPedido;
		this.fretePedido = fretePedido;
		this.statusPedido = statusPedido;
		this.comprador = comprador;
		this.vendedor = vendedor;
		this.listaItemPedido = listaItemPedido;
	}

	public PedidoDTO() {
	}

	public PedidoDTO(Pedido pedido) {
		this.id = pedido.getId();
		this.dataPedido = pedido.getDataPedido();
		this.fretePedido = pedido.getFretePedido();
		this.statusPedido = pedido.getStatusPedido();
		this.comprador = new UsuarioDTO(pedido.getComprador());
		this.vendedor = new UsuarioDTO(pedido.getVendedor());
		this.listaItemPedido = pedido.getListaItemPedido();
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

	public UsuarioDTO getComprador() {
		return comprador;
	}

	public void setComprador(UsuarioDTO comprador) {
		this.comprador = comprador;
	}

	public UsuarioDTO getVendedor() {
		return vendedor;
	}

	public void setVendedor(UsuarioDTO vendedor) {
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
		PedidoDTO other = (PedidoDTO) obj;
		return Objects.equals(comprador, other.comprador) && Objects.equals(dataPedido, other.dataPedido)
				&& Objects.equals(fretePedido, other.fretePedido) && Objects.equals(id, other.id)
				&& Objects.equals(listaItemPedido, other.listaItemPedido) && statusPedido == other.statusPedido
				&& Objects.equals(vendedor, other.vendedor);
	}
	
	public static List<PedidoDTO> converterParaPedidosDTO(List<Pedido> listaPedidos){
		List<PedidoDTO> listaPedidosDTO = new ArrayList<>();
		for (Pedido pedidoAtual : listaPedidos) {
			listaPedidosDTO.add(new PedidoDTO(pedidoAtual));
		}
		return listaPedidosDTO;
	}

}
