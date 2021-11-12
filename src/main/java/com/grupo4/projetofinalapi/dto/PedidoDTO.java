package com.grupo4.projetofinalapi.dto;

import com.grupo4.projetofinalapi.entities.ItemPedido;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.enums.StatusPedido;
import com.grupo4.projetofinalapi.groups.GruposValidacao;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedidoDTO {

	private Long id;

	@PastOrPresent(message = "Data do pedido deve ser igual ou anterior a hoje", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@DateTimeFormat (pattern = "dd/MM/yyyy")
	private LocalDateTime dataPedido;
	
	@NotNull(message = "Frete não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	private Double fretePedido;
	
	@Enumerated (EnumType.STRING)
	private StatusPedido statusPedido;
	
	@NotNull(message = "Comprador não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	private UsuarioDTO comprador;

	@NotNull(message = "Vendedor não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	private UsuarioDTO vendedor;
	
	@NotNull(message = "Lista de produtos não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	private List<ItemPedido> listaItemPedido;

	public PedidoDTO() {
	}

	public PedidoDTO(Long id, LocalDateTime dataPedido, Double fretePedido, StatusPedido statusPedido, UsuarioDTO comprador, UsuarioDTO vendedor, List<ItemPedido> listaItemPedido) {
		this.id = id;
		this.dataPedido = dataPedido;
		this.fretePedido = fretePedido;
		this.statusPedido = statusPedido;
		this.comprador = comprador;
		this.vendedor = vendedor;
		this.listaItemPedido = listaItemPedido;
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
	
	public Pedido converterParaPedido() {
		Pedido pedido = new Pedido();
		pedido.setId(this.getId());
		pedido.setDataPedido(this.getDataPedido());
		pedido.setFretePedido(this.getFretePedido());
		pedido.setStatusPedido(this.getStatusPedido());
	
		pedido.setComprador(this.getComprador().converterParaUsuario());
		pedido.setVendedor(this.getVendedor().converterParaUsuario());
		pedido.setListaItemPedido(this.getListaItemPedido());
	
		return pedido;
	}

}
