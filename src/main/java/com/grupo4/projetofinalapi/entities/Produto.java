package com.grupo4.projetofinalapi.entities;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Produto {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_produto", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String descricao;
	
	@Column (nullable = false)
	private int qtdEstoque;
	
	@Column
	private LocalDate dataFabricacao;
	
	@Column (nullable = false)
	private int tempoGarantia;
	
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	private Double precoUnitario;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_vendedor", nullable = false, columnDefinition = "int4")
	private Usuario vendedor;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_categoria", nullable = false, columnDefinition = "int4")
	private Categoria categoria;
	
	
	
	
}
