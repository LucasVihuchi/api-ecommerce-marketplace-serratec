package com.grupo4.projetofinalapi.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;

/*
 * - O endereço deverá ser validado através da API Via Cep.
 */

@Entity
public class Endereco {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "cod_endereco", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@NotBlank
	@Column (nullable = false)
	private String logradouro;
	
	@Column 
	private int numero;
	
	@NotBlank
	@Column (nullable = false, length = 8)
	private String cep;
	
	@Column 
	private String complemento;
	
	@NotBlank
	@Column (nullable = false)
	private String bairro;
	
	@NotBlank
	@Column (nullable = false)
	private String cidade;
	
	@NotBlank
	@Column (nullable = false)
	private String estado;

	@OneToMany (mappedBy = "endereco")
	private List<Usuario> listaUsuarios;

	public Endereco() {
		super();
	}

	public Endereco(Long id, @NotBlank String logradouro, int numero, @NotBlank String cep, String complemento,
			@NotBlank String bairro, @NotBlank String cidade, @NotBlank String estado, List<Usuario> listaUsuarios) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.listaUsuarios = listaUsuarios;
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

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", logradouro=" + logradouro + ", numero=" + numero + ", cep=" + cep
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado
				+ ", listaUsuarios=" + listaUsuarios + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, cidade, complemento, estado, id, listaUsuarios, logradouro, numero);
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
				&& Objects.equals(estado, other.estado) && Objects.equals(id, other.id)
				&& Objects.equals(listaUsuarios, other.listaUsuarios) && Objects.equals(logradouro, other.logradouro)
				&& numero == other.numero;
	}
	
	public static boolean enderecoEhValido(Endereco endereco, EnderecoDTO enderecoDTO) {
		boolean logradouroValido = endereco.getLogradouro() == enderecoDTO.getLogradouro();
		boolean bairroValido = endereco.getBairro() == enderecoDTO.getBairro();
		boolean cidadeValida = endereco.getCidade() == enderecoDTO.getLocalidade();		
				
		return logradouroValido && bairroValido && cidadeValida;
	}
	
}
