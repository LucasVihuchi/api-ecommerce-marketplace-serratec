package com.grupo4.projetofinalapi.dto;

import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.enums.StatusPedido;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** DTO da entidade pedido
 */
public class PedidoDTO {

	@ApiModelProperty(value = "Identificador único do pedido")
	private Long id;

	@PastOrPresent(message = "Data do pedido deve ser igual ou anterior a hoje", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@ApiModelProperty(value = "Data de finalização do pedido")
	private LocalDateTime dataPedido;
	
	@NotNull(message = "Frete não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@PositiveOrZero(message = "Frete não pode ser negativo", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@ApiModelProperty(value = "Frete do pedido", required = true)
	private Double fretePedido;
	
	@Enumerated (EnumType.STRING)
	@ApiModelProperty(value = "Status do pedido")
	private StatusPedido statusPedido;

	@ApiModelProperty(value = "Comprador do pedido")
	private UsuarioDTO comprador;

	@NotNull(message = "Vendedor não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ApiModelProperty(value = "Vendedor do pedido", required = true)
	private UsuarioDTO vendedor;
	
	@NotNull(message = "Lista de produtos não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ApiModelProperty(value = "Lista de item de pedido do pedido", required = true)
	private List<ItemPedidoDTO> listaItemPedido;

	public PedidoDTO() {
	}

	public PedidoDTO(Long id,
					 LocalDateTime dataPedido,
					 Double fretePedido,
					 StatusPedido statusPedido,
					 UsuarioDTO comprador,
					 UsuarioDTO vendedor,
					 List<ItemPedidoDTO> listaItemPedido) {
		this.id = id;
		this.dataPedido = dataPedido;
		this.fretePedido = fretePedido;
		this.statusPedido = statusPedido;
		this.comprador = comprador;
		this.vendedor = vendedor;
		this.listaItemPedido = listaItemPedido;
	}

	/** Construtor que constrói um pedidoDTO a partir de um pedido
	 *
	 * @param pedido pedido que será convertido
	 */
	public PedidoDTO(Pedido pedido) {
		this.id = pedido.getId();
		this.dataPedido = pedido.getDataPedido();
		this.fretePedido = pedido.getFretePedido();
		this.statusPedido = pedido.getStatusPedido();
		this.comprador = new UsuarioDTO(pedido.getComprador());
		this.vendedor = new UsuarioDTO(pedido.getVendedor());
		this.listaItemPedido = ItemPedidoDTO.converterParaListaItemPedidoDTO(pedido.getListaItemPedido());
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

	public List<ItemPedidoDTO> getListaItemPedido() {
		return listaItemPedido;
	}

	public void setListaItemPedido(List<ItemPedidoDTO> listaItemPedido) {
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

	/** Método para converter um List de pedidos em um List de pedidosDTO
	 *
	 * @param listaPedidos List de pedidos que serão convertidos
	 * @return List de pedidoDTO convertido
	 */
	public static List<PedidoDTO> converterParaPedidosDTO(List<Pedido> listaPedidos){
		List<PedidoDTO> listaPedidosDTO = new ArrayList<>();
		for (Pedido pedidoAtual : listaPedidos) {
			listaPedidosDTO.add(new PedidoDTO(pedidoAtual));
		}
		return listaPedidosDTO;
	}

	/** Método para converter um pedidoDTO em pedido
	 *
	 * @return pedido convertido
	 */
	public Pedido converterParaPedido() {
		Pedido pedido = new Pedido();
		pedido.setId(this.getId());
		pedido.setDataPedido(this.getDataPedido());
		pedido.setFretePedido(this.getFretePedido());
		pedido.setStatusPedido(this.getStatusPedido());

		if(this.getComprador() != null) {
			pedido.setComprador(this.getComprador().converterParaUsuario());
		}
		if(this.getVendedor() != null) {
			pedido.setVendedor(this.getVendedor().converterParaUsuario());
		}
		if(this.listaItemPedido != null) {
			pedido.setListaItemPedido(ItemPedidoDTO.converterParaListaItemPedido(this.getListaItemPedido()));
		}

		return pedido;
	}

}
