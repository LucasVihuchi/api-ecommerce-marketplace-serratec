package com.grupo4.projetofinalapi.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity
public class Endereco {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "cod_endereco", nullable = false, columnDefinition = "serial")
	private Long id;
	
	@Column (nullable = false)
	private String logradouro;
	
	@Column 
	private int numero;
	
	@Column (nullable = false, length = 8)
	private String cep;
	
	@Column 
	private String complemento;
	
	@Column (nullable = false)
	private String bairro;
	
	@Column (nullable = false)
	private String cidade;
	
	@Column (nullable = false)
	private String estado;

	@OneToMany (mappedBy = "endereco")
	private List<Usuario> listaUsuarios;
	
	
}
