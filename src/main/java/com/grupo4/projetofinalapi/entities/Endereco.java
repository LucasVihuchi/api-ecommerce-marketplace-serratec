package com.grupo4.projetofinalapi.entities;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Endereco {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "cod_endereco", nullable = false, columnDefinition = "serial")
	@ApiModelProperty(value = "Identificador único do endereço")
	private Long id;
	
	@NotBlank(message = "Logradouro não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Logradouro do endereço", required = true)
	private String logradouro;
	
	@Column
	@ApiModelProperty(value = "Número do endereço")
	private int numero;
	
	@NotBlank(message = "CEP não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Size(min = 8, max = 8, message = "Tamanho deve ser de {min} caracteres", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Column (nullable = false, length = 8)
	@ApiModelProperty(value = "CEP do endereço", required = true)
	private String cep;
	
	@Column
	@ApiModelProperty(value = "Complemento do endereço")
	private String complemento;
	
	@NotBlank(message = "Bairro não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Bairro do endereço", required = true)
	private String bairro;
	
	@NotBlank(message = "Cidade não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Cidade do endereço", required = true)
	private String cidade;
	
	@NotBlank(message = "Estado não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Column (nullable = false)
	@ApiModelProperty(value = "Estado do endereço", required = true)
	private String estado;

	public Endereco() {
		super();
	}

	public Endereco(Long id,
					String logradouro,
					int numero,
					String cep,
					String complemento,
					String bairro,
					String cidade,
					String estado) {
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}

	public void preencherDadosViaCep(EnderecoDTO enderecoDTO) {
		this.setBairro(enderecoDTO.getBairro());
		this.setCidade(enderecoDTO.getLocalidade());
		this.setEstado(enderecoDTO.getUf());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
    @Override
	public int hashCode() {
		return Objects.hash(bairro, cep, cidade, complemento, estado, id, logradouro, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(cidade, other.cidade) && Objects.equals(complemento, other.complemento)
				&& Objects.equals(estado, other.estado)
				&& Objects.equals(logradouro, other.logradouro)
				&& numero == other.numero;
	}
}
