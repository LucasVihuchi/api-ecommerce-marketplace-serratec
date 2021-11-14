package com.grupo4.projetofinalapi.entities;

import com.grupo4.projetofinalapi.groups.GruposValidacao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

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
	
	@NotBlank(message = "Nome não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	private String nome;
	
	@NotBlank(message = "Descrição não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	private String descricao;

	public Categoria() {
	}

	public Categoria(Long id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
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
