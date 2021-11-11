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

	public Categoria(Long id, @NotBlank String nome, @NotBlank String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	public Categoria() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descricao, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		return Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}

	
	
	
	
	
}
