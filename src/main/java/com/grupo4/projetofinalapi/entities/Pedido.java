package com.grupo4.projetofinalapi.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.grupo4.projetofinalapi.enums.StatusPedido;

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
	
	// TODO Criar validação de data
	@NotNull
	@PastOrPresent
	@DateTimeFormat (pattern = "yyyy-MM-dd")
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
	

}
