package com.grupo4.projetofinalapi.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_categoria", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String descricao;

	@OneToMany (mappedBy = "categoria")
	private List<Produto> listaProdutos;
	
	
}
