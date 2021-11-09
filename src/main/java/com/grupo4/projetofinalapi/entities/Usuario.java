package com.grupo4.projetofinalapi.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
import com.grupo4.projetofinalapi.validations.ValidDataNascimento;
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
	
	@NotNull
	@ValidDataNascimento
	@Past
	@DateTimeFormat (pattern = "dd/MM/yyyy")
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

	
	public Usuario() {
		super();
	}


	public Usuario(Long id, @NotBlank String nome, @NotBlank String sobrenome, @NotNull Sexo sexo,
			@NotBlank String telefonePrincipal, @NotBlank String telefoneSecundario, @NotBlank String nomeUsuario,
			@NotBlank String senhaUsuario, @NotBlank @Email String email, @NotNull @CPF String cpf,
			@NotNull @Past LocalDate dataNascimento, @NotNull boolean ehVendedor, Endereco endereco,
			List<Produto> listaProdutos, List<Pedido> listaPedidosFeitos, List<Pedido> listaPedidosRecebidos) {
		super();
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.sexo = sexo;
		this.telefonePrincipal = telefonePrincipal;
		this.telefoneSecundario = telefoneSecundario;
		this.nomeUsuario = nomeUsuario;
		this.senhaUsuario = senhaUsuario;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.ehVendedor = ehVendedor;
		this.endereco = endereco;
		this.listaProdutos = listaProdutos;
		this.listaPedidosFeitos = listaPedidosFeitos;
		this.listaPedidosRecebidos = listaPedidosRecebidos;
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getTelefonePrincipal() {
		return telefonePrincipal;
	}

	public void setTelefonePrincipal(String telefonePrincipal) {
		this.telefonePrincipal = telefonePrincipal;
	}

	public String getTelefoneSecundario() {
		return telefoneSecundario;
	}

	public void setTelefoneSecundario(String telefoneSecundario) {
		this.telefoneSecundario = telefoneSecundario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public boolean isEhVendedor() {
		return ehVendedor;
	}

	public void setEhVendedor(boolean ehVendedor) {
		this.ehVendedor = ehVendedor;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Pedido> getListaPedidosFeitos() {
		return listaPedidosFeitos;
	}

	public void setListaPedidosFeitos(List<Pedido> listaPedidosFeitos) {
		this.listaPedidosFeitos = listaPedidosFeitos;
	}

	public List<Pedido> getListaPedidosRecebidos() {
		return listaPedidosRecebidos;
	}

	public void setListaPedidosRecebidos(List<Pedido> listaPedidosRecebidos) {
		this.listaPedidosRecebidos = listaPedidosRecebidos;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", sexo=" + sexo
				+ ", telefonePrincipal=" + telefonePrincipal + ", telefoneSecundario=" + telefoneSecundario
				+ ", nomeUsuario=" + nomeUsuario + ", senhaUsuario=" + senhaUsuario + ", email=" + email + ", cpf="
				+ cpf + ", dataNascimento=" + dataNascimento + ", ehVendedor=" + ehVendedor + ", endereco=" + endereco
				+ ", listaProdutos=" + listaProdutos + ", listaPedidosFeitos=" + listaPedidosFeitos
				+ ", listaPedidosRecebidos=" + listaPedidosRecebidos + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(cpf, dataNascimento, ehVendedor, email, endereco, id, listaPedidosFeitos,
				listaPedidosRecebidos, listaProdutos, nome, nomeUsuario, senhaUsuario, sexo, sobrenome,
				telefonePrincipal, telefoneSecundario);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(dataNascimento, other.dataNascimento)
				&& ehVendedor == other.ehVendedor && Objects.equals(email, other.email)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id)
				&& Objects.equals(listaPedidosFeitos, other.listaPedidosFeitos)
				&& Objects.equals(listaPedidosRecebidos, other.listaPedidosRecebidos)
				&& Objects.equals(listaProdutos, other.listaProdutos) && Objects.equals(nome, other.nome)
				&& Objects.equals(nomeUsuario, other.nomeUsuario) && Objects.equals(senhaUsuario, other.senhaUsuario)
				&& sexo == other.sexo && Objects.equals(sobrenome, other.sobrenome)
				&& Objects.equals(telefonePrincipal, other.telefonePrincipal)
				&& Objects.equals(telefoneSecundario, other.telefoneSecundario);
	}
	
	
	
	
}
