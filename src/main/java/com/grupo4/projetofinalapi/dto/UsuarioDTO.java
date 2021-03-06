package com.grupo4.projetofinalapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.Sexo;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.validations.ValidDataNascimento;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.time.LocalDate;

/** DTO da entidade usuário
 */
@JsonIgnoreProperties(value = {"ehVendedor", "bairro", "cidade", "estado"}, allowGetters = true)
public class UsuarioDTO {

	@ApiModelProperty(value = "Identificador único do usuário")
	private Long id;

	@NotBlank(message = "Nome não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ApiModelProperty(value = "Nome do usuário", required = true)
	private String nome;

	@NotBlank(message = "Sobrenome não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ApiModelProperty(value = "Sobrenome do usuário", required = true)
	private String sobrenome;

	@NotNull(message = "Sexo não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ApiModelProperty(value = "Sexo do usuário", required = true)
	private Sexo sexo;

	@NotBlank(message = "Telefone principal não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Size(min = 8, max = 13, message = "Telefone deve conter entre {min} e {max} dígitos", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Pattern(regexp = "^[0-9]{8,13}$", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class}, message = "Telefone principal do usuário deve conter de 8 até 13 números")
	@ApiModelProperty(value = "Telefone principal do usuário", required = true)
	private String telefonePrincipal;

	@Size(min = 8, max = 13, message = "Telefone deve conter entre {min} e {max} dígitos", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Pattern(regexp = "^[0-9]{8,13}$", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class}, message = "Telefone secundário do usuário deve conter de 8 até 13 números")
	@ApiModelProperty(value = "Telefone secundário do usuário")
	private String telefoneSecundario;

	@NotBlank(message = "Nome de usuário não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ApiModelProperty(value = "Nome de usuário do usuário", required = true)
	private String nomeUsuario;

	@NotBlank(message = "Senha não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Size(min = 8, max = 35, message = "Senha deve conter entre {min} e {max} caracteres", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@ApiModelProperty(value = "Senha do usuário", required = true)
	private String senhaUsuario;

	@NotBlank(message = "E-mail não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@Email(message = "E-mail inválido", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@ApiModelProperty(value = "Email do usuário", required = true)
	private String email;

	@NotBlank(message = "CPF não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
	@CPF(message = "CPF inválido", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@ApiModelProperty(value = "CPF do usuário", required = true)
	private String cpf;

	@NotNull(message = "Data de nascimento não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
	@ValidDataNascimento(message = "Usuário deve ser possuir mais de 18 anos ou menos de 120", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@Past(message = "Data de nascimento deve ser anterior a hoje", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
	@ApiModelProperty(value = "Data de nascimento do usuário", required = true)
	private LocalDate dataNascimento;

	@ApiModelProperty(value = "Indica se o usuário é um vendedor", required = true)
	private boolean ehVendedor;

	@JsonIgnoreProperties(value = {"estado", "cidade", "bairro"}, allowGetters = true)
	@NotNull(message = "Endereço não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
	@ApiModelProperty(value = "Endereço do usuário", required = true)
	private Endereco endereco;

	public UsuarioDTO(Long id,
					  String nome,
					  String sobrenome,
					  Sexo sexo,
					  String telefonePrincipal,
					  String telefoneSecundario,
					  String nomeUsuario,
					  String senhaUsuario,
					  String email,
					  String cpf,
					  LocalDate dataNascimento,
					  boolean ehVendedor,
					  Endereco endereco) {
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

	/** Construtor que constrói um usuárioDTO a partir de um usuário
	 *
	 * @param usuario usuário que será convertido
	 */
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

	/** Método para converter um usuárioDTO para Usuário
	 *
	 * @return UsuárioDTO convertido para usuário
	 */
	public Usuario converterParaUsuario() {
		return new Usuario(
				this.id,
				this.nome,
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
