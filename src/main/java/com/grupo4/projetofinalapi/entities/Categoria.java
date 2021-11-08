package com.grupo4.projetofinalapi.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/*
 * Temos que fazer o GetAll e o GetCategoriaPorNome; post
 * - Visualizar todas as categorias ou uma categoria especifica pelo nome.
 * - Criar uma nova categoria.
 * -
 */


@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_categoria", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@NotBlank
	@Column (nullable = false)
	private String nome;
	
	@NotBlank
	@Column (nullable = false)
	private String descricao;

	@OneToMany (mappedBy = "categoria")
	private List<Produto> listaProdutos;
	
	
	
	
}
