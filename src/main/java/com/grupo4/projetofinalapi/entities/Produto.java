package com.grupo4.projetofinalapi.entities;

import com.grupo4.projetofinalapi.groups.GruposValidacao;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

/** Classe que representa a entidade produto do banco de dados
 */
@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_produto", nullable = false, columnDefinition = "serial")
	@ApiModelProperty(value = "Identificador único do produto")
	private Long id;
	
	@NotBlank(message = "Nome não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Nome do produto", required = true)
	private String nome;
	
	@NotBlank(message = "Descrição não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Descrição do produto", required = true)
	private String descricao;
	
	@NotNull(message = "Quantidade em estoque não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
	@PositiveOrZero(message = "Quantidade não pode ser negativa ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Quantidade de estoque do produto", required = true)
	private int qtdEstoque;
	
	@Past(message = "Data deve ser anterior a hoje", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column
	@ApiModelProperty(value = "Data de fabricação do produto")
	private LocalDate dataFabricacao;
	
	@NotNull(message = "Tempo de garantia não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@PositiveOrZero(message = "Tempo de garantia não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Tempo de garantia do produto", required = true)
	private int tempoGarantia;
	
	@Positive(message = "Preço unitário não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@DecimalMax (value = "99999.99", message = "Preço unitário não pode ser superior a R$ {value}", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false, columnDefinition = "numeric(7,2)")
	@ApiModelProperty(value = "Preço unitário do produto", required = true)
	private Double precoUnitario;
	
	@NotNull(message = "Vendedor não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_vendedor", nullable = false, columnDefinition = "int4")
	@ApiModelProperty(value = "Vendedor do produto", required = true)
	private Usuario vendedor;
	
	@NotNull(message = "Categoria não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn (name = "cod_categoria", nullable = false, columnDefinition = "int4")
	@ApiModelProperty(value = "Categoria do produto", required = true)
	private Categoria categoria;

	@OneToOne(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ApiModelProperty(value = "Foto do produto", required = true)
	private FotoProduto foto;

	public Produto() {
	}

	public Produto(Long id,
				   String nome,
				   String descricao,
				   int qtdEstoque,
				   LocalDate dataFabricacao,
				   int tempoGarantia,
				   Double precoUnitario,
				   Usuario vendedor,
				   Categoria categoria) {
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

	public FotoProduto getFoto() {
		return foto;
	}

	public void setFoto(FotoProduto foto) {
		this.foto = foto;
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
