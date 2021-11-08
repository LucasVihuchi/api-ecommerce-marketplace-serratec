package com.grupo4.projetofinalapi.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.grupo4.projetofinalapi.enums.StatusPedido;


@Entity
@Table (uniqueConstraints = {
		@UniqueConstraint (name = "PedidoUnico", columnNames = {"cod_comprador", "cod_vendedor", "data_pedido"})
})
public class Pedido {
		
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "cod_pedido", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@Column (name = "data_pedido", nullable = false)
	private LocalDateTime dataPedido;
	
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
