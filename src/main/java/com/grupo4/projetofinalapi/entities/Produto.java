package com.grupo4.projetofinalapi.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

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
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFabricacao;
	
	@NotNull
	@PositiveOrZero
	@Column (nullable = false)
	private int tempoGarantia;
	
	@Positive
	@DecimalMax (value = "99999.99")
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	private Double precoUnitario;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_vendedor", nullable = false, columnDefinition = "int4")
	// TODO Verificar se JSONIgnore já resolve a questão dos dados
	private Usuario vendedor;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_categoria", nullable = false, columnDefinition = "int4")
	private Categoria categoria;
	
	
	
	
}
