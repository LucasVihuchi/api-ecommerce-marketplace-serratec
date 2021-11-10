package com.grupo4.projetofinalapi.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.Sexo;
import com.grupo4.projetofinalapi.validations.ValidDataNascimento;


public class UsuarioDTO {

	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String sobrenome;
	@NotNull
	private Sexo sexo;
	@NotBlank
	private String telefonePrincipal;
	private String telefoneSecundario;
	@NotBlank
	private String nomeUsuario;
	@NotBlank
	private String senhaUsuario;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@CPF
	private String cpf;
	@NotNull
	@ValidDataNascimento
	@Past
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	@NotNull
	private boolean ehVendedor;
	@NotNull
	private Endereco endereco;
	
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
	public UsuarioDTO(Long id, String nome, String sobrenome, Sexo sexo, String telefonePrincipal,
			String telefoneSecundario, String nomeUsuario, String senhaUsuario, String email, String cpf,
			LocalDate dataNascimento, boolean ehVendedor, Endereco endereco) {
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
	}
	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.sexo = usuario.getSexo();
		this.telefonePrincipal = usuario.getTelefonePrincipal();
		this.telefoneSecundario = usuario.getTelefoneSecundario();
		this.nomeUsuario = usuario.getNomeUsuario();
		this.senhaUsuario = usuario.getSenhaUsuario();
		this.email = usuario.getEmail();
		this.cpf = usuario.getCpf();
		this.dataNascimento = usuario.getDataNascimento();
		this.ehVendedor = usuario.isEhVendedor();
		this.endereco = usuario.getEndereco();	
	}
	public Usuario converterParaUsuario() {
		return new Usuario(this.nome,
				this.sobrenome,
				this.sexo, 
				this.telefonePrincipal,
				this.telefoneSecundario,
				this.nomeUsuario,
				this.senhaUsuario,
				this.email,
				this.cpf,
				this.dataNascimento, 
				this.ehVendedor,
				this.endereco);
		
	}
	

}
