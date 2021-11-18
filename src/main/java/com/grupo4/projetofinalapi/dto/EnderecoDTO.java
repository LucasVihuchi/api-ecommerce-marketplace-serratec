package com.grupo4.projetofinalapi.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/** DTO de pedido para recebimento de resposta da API de endereço do site ViaCEP
 */
public class EnderecoDTO {

	@ApiModelProperty(value = "CEP do endereço", required = true)
	private String cep;

	@ApiModelProperty(value = "Logradouro do endereço", required = true)
	private String logradouro;

	@ApiModelProperty(value = "Complemento do endereço")
	private String complemento;

	@ApiModelProperty(value = "Bairro do endereço")
	private String bairro;

	@ApiModelProperty(value = "Localidade do endereço", required = true)
	private String localidade;

	@ApiModelProperty(value = "UF do endereço", required = true)
	private String uf;

	@ApiModelProperty(value = "Registro IBGE do endereço", required = true)
	private String ibge;

	@ApiModelProperty(value = "GIA do endereço", required = true)
	private String gia;

	@ApiModelProperty(value = "DDD do endereço", required = true)
	private String ddd;

	@ApiModelProperty(value = "Siafi do endereço", required = true)
	private String siafi;

	@ApiModelProperty(value = "Erro do endereço", required = true)
	private boolean erro;
	
	public EnderecoDTO(){
	}

	public EnderecoDTO(String cep,
					   String logradouro,
					   String complemento,
					   String bairro,
					   String localidade,
					   String uf, String ibge,
					   String gia,
					   String ddd,
					   String siafi,
					   boolean erro) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
		this.ibge = ibge;
		this.gia = gia;
		this.ddd = ddd;
		this.siafi = siafi;
		this.erro = erro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
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

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getGia() {
		return gia;
	}

	public void setGia(String gia) {
		this.gia = gia;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getSiafi() {
		return siafi;
	}

	public void setSiafi(String siafi) {
		this.siafi = siafi;
	}

	public boolean isErro() {
		return erro;
	}

	public void setErro(boolean erro) {
		this.erro = erro;
	}

	@Override
	public String toString() {
		return "EnderecoDTO [cep=" + cep + ", logradouro=" + logradouro + ", complemento=" + complemento + ", bairro="
				+ bairro + ", localidade=" + localidade + ", uf=" + uf + ", ibge=" + ibge + ", gia=" + gia + ", ddd="
				+ ddd + ", siafi=" + siafi + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EnderecoDTO that = (EnderecoDTO) o;
		return erro == that.erro && Objects.equals(cep, that.cep) && Objects.equals(logradouro, that.logradouro) && Objects.equals(complemento, that.complemento) && Objects.equals(bairro, that.bairro) && Objects.equals(localidade, that.localidade) && Objects.equals(uf, that.uf) && Objects.equals(ibge, that.ibge) && Objects.equals(gia, that.gia) && Objects.equals(ddd, that.ddd) && Objects.equals(siafi, that.siafi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cep, logradouro, complemento, bairro, localidade, uf, ibge, gia, ddd, siafi, erro);
	}
}
