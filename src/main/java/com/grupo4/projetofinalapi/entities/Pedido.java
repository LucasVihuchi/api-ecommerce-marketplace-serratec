package com.grupo4.projetofinalapi.entities;

import com.grupo4.projetofinalapi.enums.StatusPedido;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/** Classe que representa a entidade pedido do banco de dados
 */
@Entity
@Table (uniqueConstraints = {
		@UniqueConstraint (name = "PedidoUnico", columnNames = {"cod_comprador", "cod_vendedor", "data_pedido"})
})
public class Pedido {
		
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "cod_pedido", nullable = false, columnDefinition = "serial")
	@ApiModelProperty(value = "Identificador único da pedido")
	private Long id;

	@NotNull
	@PastOrPresent
	@Column (name = "data_pedido", nullable = false)
	@ApiModelProperty(value = "Data de realização do pedido", required = true)
	private LocalDateTime dataPedido;
	
	@NotNull
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	@ApiModelProperty(value = "Frete do pedido")
	private Double fretePedido;
	
	@Enumerated (EnumType.STRING)
	@Column (nullable = false)
	@ApiModelProperty(value = "Status do pedido")
	private StatusPedido statusPedido;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_comprador", nullable = false, columnDefinition = "int4")
	@ApiModelProperty(value = "Comprador do pedido", required = true)
	private Usuario comprador;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_vendedor", nullable = false, columnDefinition = "int4")
	@ApiModelProperty(value = "Vendedor do pedido", required = true)
	private Usuario vendedor;
	
	@OneToMany (mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@ApiModelProperty(value = "Lista de itens do pedido", required = true)
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

	/** Método para gerar o corpo do email de finalização de pedido
	 *
	 * @return String formatada em HTML com o corpo da mensagem
	 */
	public String gerarTemplateEmail() {
		DateTimeFormatter formatoDataHoraBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		DateTimeFormatter formatoDataBraileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataEnvio = dataPedido.toLocalDate().plusDays(2);
		LocalDate dataEntrega = dataPedido.toLocalDate().plusWeeks(2);
		System.out.println("\n\n\n -------------jrthrtjrtj----------\n\n\n\n");
		String conteudo =
				"<h2>Pedido Nº " + this.getId() + "</h2>" +
				"<p>Data de finalização do pedido: " + formatoDataHoraBrasileiro.format(dataPedido) + "</p>" +
				"<p>Data de envio: " + formatoDataBraileiro.format(dataEnvio) + "</p>" +
				"<p>Data de entrega: " + formatoDataBraileiro.format(dataEntrega) + "</p>";

		double totalPedido = 0;
		String tabelaProdutos =
				"<table>" +
				"<tr>" +
				"<th>Quantidade</th>" +
				"<th>Produto</th>" +
				"<th>Preço Unitário" +
				"<th>Subtotal" +
				"</tr>";
		for(ItemPedido itemPedidoAtual : this.listaItemPedido) {
			tabelaProdutos = tabelaProdutos.concat(
					"<tr>" +
					"<td>" + itemPedidoAtual.getQuantidade() + "</td>" +
					"<td>" + itemPedidoAtual.getProduto().getNome() + "</td>" +
					"<td>R$ " + String.format("%.2f", itemPedidoAtual.getPrecoUnitario()) + "</td>" +
					"<td>R$ " + String.format("%.2f", itemPedidoAtual.getQuantidade() * itemPedidoAtual.getPrecoUnitario()) + "</td>" +
					"</tr>");
			totalPedido += itemPedidoAtual.getQuantidade() * itemPedidoAtual.getPrecoUnitario();
		}
		totalPedido += this.fretePedido;
		tabelaProdutos = tabelaProdutos.concat("</table>");
		conteudo = conteudo.concat(tabelaProdutos);

		return conteudo.concat("<h4>Freto do pedido: R$ " + String.format("%.2f", this.fretePedido) + "<h4>Preço total do pedido: R$ " + String.format("%.2f", totalPedido) + "</h4>");
	}
}
