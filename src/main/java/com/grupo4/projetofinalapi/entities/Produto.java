package com.grupo4.projetofinalapi.entities;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/*
 * - Visualizar todos os produtos ou um produto específico pelo nome
 * - Criar um novo produto (com imagem).
 * 
 */


@Entity
public class Produto {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_produto", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@NotBlank
	@Column (nullable = false)
	private String nome;
	
	@NotBlank
	@Column (nullable = false)
	private String descricao;
	
	@NotNull
	@PositiveOrZero
	@Column (nullable = false)
	private int qtdEstoque;
	
	@Past
	@Column
	private LocalDate dataFabricacao;
	
	@NotNull
	@Column (nullable = false)
	private int tempoGarantia;
	
	@Positive
	@DecimalMax (value = "99999.99")
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	private Double precoUnitario;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_vendedor", nullable = false, columnDefinition = "int4")
	private Usuario vendedor;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_categoria", nullable = false, columnDefinition = "int4")
	private Categoria categoria;
	
	
	
	
}
