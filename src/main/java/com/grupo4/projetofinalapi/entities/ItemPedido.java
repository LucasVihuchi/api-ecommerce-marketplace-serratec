package com.grupo4.projetofinalapi.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table (uniqueConstraints = {
		@UniqueConstraint (name = "ItemPedidoUnico", columnNames = {"cod_produto", "cod_pedido"})
})

public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_item_pedido", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@NotNull
	@Column (nullable = false)
	private int quantidade;
	
	@NotBlank
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	private Double precoUnitario;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_produto", nullable = false, columnDefinition = "int4")
	private Produto produto;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_pedido", nullable = false, columnDefinition = "int4")
	private Pedido pedido;
	
}
