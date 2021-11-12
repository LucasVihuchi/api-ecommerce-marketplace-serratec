package com.grupo4.projetofinalapi.entities;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.groups.GruposValidacao.ValidadorPost;
import com.grupo4.projetofinalapi.groups.GruposValidacao.ValidadorPut;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

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
	
	@NotBlank(message = "Nome não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	private String nome;
	
	@NotBlank(message = "Descrição não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	private String descricao;
	
	@NotNull(message = "Quantidade em estoque não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
	@PositiveOrZero(message = "Quantidade não pode ser negativa ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false)
	private int qtdEstoque;
	
	@Past(message = "Data deve ser anterior a hoje", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFabricacao;
	
	@NotNull(message = "Tempo de garantia não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@PositiveOrZero(message = "Tempo de garantia não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false)
	private int tempoGarantia;
	
	@Positive(message = "Preço unitário não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@DecimalMax (value = "99999.99", message = "Preço unitário não pode ser superior a R$ {value}", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	private Double precoUnitario;
	
	@NotNull(message = "Vendedor não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_vendedor", nullable = false, columnDefinition = "int4")
	// TODO Verificar se JSONIgnore já resolve a questão dos dados :D
	private Usuario vendedor;
	
	@NotNull(message = "Categoria não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_categoria", nullable = false, columnDefinition = "int4")
	private Categoria categoria;

	public Produto() {
	}

	public Produto(Long id,
			@NotBlank(message = "Nome não pode ficar em branco ou nulo", groups = ValidadorPost.class) String nome,
			@NotBlank(message = "Descrição não pode ficar em branco ou nulo", groups = ValidadorPost.class) String descricao,
			@NotNull(message = "Quantidade em estoque não pode ser nula", groups = ValidadorPost.class) @PositiveOrZero(message = "Quantidade não pode ser negativa ou zero", groups = {
					ValidadorPost.class, ValidadorPut.class }) int qtdEstoque,
			@Past(message = "Data deve ser anterior a hoje", groups = { ValidadorPost.class,
					ValidadorPut.class }) LocalDate dataFabricacao,
			@NotNull(message = "Tempo de garantia não pode ser nulo", groups = ValidadorPost.class) @PositiveOrZero(message = "Tempo de garantia não pode ser negativo ou zero", groups = {
					ValidadorPost.class, ValidadorPut.class }) int tempoGarantia,
			@Positive(message = "Preço unitário não pode ser negativo ou zero", groups = { ValidadorPost.class,
					ValidadorPut.class }) @DecimalMax(value = "99999.99", message = "Preço unitário não pode ser superior a R$ {value}", groups = {
							ValidadorPost.class, ValidadorPut.class }) Double precoUnitario,
			@NotNull(message = "Vendedor não pode ser nulo", groups = ValidadorPost.class) Usuario vendedor,
			@NotNull(message = "Categoria não pode ser nula", groups = ValidadorPost.class) Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.qtdEstoque = qtdEstoque;
		this.dataFabricacao = dataFabricacao;
		this.tempoGarantia = tempoGarantia;
		this.precoUnitario = precoUnitario;
		this.vendedor = vendedor;
		this.categoria = categoria;
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

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public LocalDate getDataFabricacao() {
		return dataFabricacao;
	}

	public void setDataFabricacao(LocalDate dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
	}

	public int getTempoGarantia() {
		return tempoGarantia;
	}

	public void setTempoGarantia(int tempoGarantia) {
		this.tempoGarantia = tempoGarantia;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, dataFabricacao, descricao, id, nome, precoUnitario, qtdEstoque, tempoGarantia,
				vendedor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(dataFabricacao, other.dataFabricacao)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(precoUnitario, other.precoUnitario)
				&& qtdEstoque == other.qtdEstoque && tempoGarantia == other.tempoGarantia
				&& Objects.equals(vendedor, other.vendedor);
	}
	
	
	
}
