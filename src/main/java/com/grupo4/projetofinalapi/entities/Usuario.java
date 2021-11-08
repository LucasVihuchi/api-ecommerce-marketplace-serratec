package com.grupo4.projetofinalapi.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.grupo4.projetofinalapi.enums.Sexo;
/*
 * Precisamos fazer o metodo post, put, delete:
 * - Atualizar seus próprios dados pessoais.
 * - Deletar sua própria conta.
 * - Um cliente poderá se cadastrar livremente.
 */
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_usuario",nullable = false, columnDefinition = "serial")
	private Long id;
	
	@NotBlank
	@Column (nullable = false)
	private String nome;
	
	@NotBlank
	@Column (nullable = false)
	private String sobrenome;
	
	@NotNull
	@Enumerated (EnumType.STRING)
	@Column (nullable = false)
	private Sexo sexo;
	
	@NotBlank
	@Column (nullable = false, length = 13)
	private String telefonePrincipal;

	@NotBlank
	@Column (length = 13)
	private String telefoneSecundario;
	
	@NotBlank
	@Column (nullable = false, unique = true)
	private String nomeUsuario;
	
	@NotBlank
	@Column (nullable = false, length = 35)
	private String senhaUsuario;
	
	@NotBlank
	@Email
	@Column (nullable = false, unique = true)
	private String email;
	
	@NotNull
	@CPF
	@Column (nullable = false, length = 11, unique = true)
	private String cpf;
	
	// TODO Validar data de nascimento
	@NotNull
	@Past
	@DateTimeFormat (pattern = "yyyy-MM-dd")
	@Column (nullable = false)
	private LocalDate dataNascimento;
	
	@NotNull
	@Column (nullable = false)
	private boolean ehVendedor;
	
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn (name = "cod_endereco", nullable = false, columnDefinition = "int4")
	private Endereco endereco;
	
	@OneToMany (mappedBy = "vendedor")
	private List<Produto> listaProdutos;
	
	@OneToMany (mappedBy = "comprador")
	private List<Pedido> listaPedidosFeitos;
		
	@OneToMany (mappedBy = "vendedor")
	private List<Pedido> listaPedidosRecebidos;
	
	
	
}
