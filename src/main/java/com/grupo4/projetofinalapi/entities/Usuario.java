package com.grupo4.projetofinalapi.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.br.CPF;

import com.grupo4.projetofinalapi.enums.Sexo;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "cod_usuario",nullable = false, columnDefinition = "serial")
	private Long id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String sobrenome;
	
	@Enumerated (EnumType.STRING)
	@Column (nullable = false)
	private Sexo sexo;
	
	@Column (nullable = false, length = 13)
	private String telefonePrincipal;

	@Column (length = 13)
	private String telefoneSecundario;
	
	@Column (nullable = false, unique = true)
	private String nomeUsuario;
	
	@Column (nullable = false, length = 35)
	private String senhaUsuario;
	
	@Column (nullable = false, unique = true)
	private String email;
	
	@Column (nullable = false, length = 11, unique = true)
	private String cpf;
	
	@Column (nullable = false)
	private LocalDate dataNascimento;
	
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
