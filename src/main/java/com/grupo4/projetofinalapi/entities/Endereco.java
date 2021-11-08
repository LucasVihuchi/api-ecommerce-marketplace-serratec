package com.grupo4.projetofinalapi.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
	
	
}
