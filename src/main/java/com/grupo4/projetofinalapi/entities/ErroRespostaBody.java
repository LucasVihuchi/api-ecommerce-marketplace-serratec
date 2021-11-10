package com.grupo4.projetofinalapi.entities;

import java.time.LocalDateTime;
import java.util.List;

public class ErroRespostaBody {
	private LocalDateTime horarioErro;
	private int status;
	private String titulo;
	private List<String> listaErros;
	
	
	public ErroRespostaBody() {
		this.horarioErro = LocalDateTime.now();
	}


	public ErroRespostaBody(LocalDateTime horarioErro, int status, String titulo, List<String> listaErros) {
		this();
		if(horarioErro != null) {	
			this.horarioErro = horarioErro;
		}
		this.status = status;
		this.titulo = titulo;
		this.listaErros = listaErros;
	}


	public LocalDateTime getHorarioErro() {
		return horarioErro;
	}


	public void setHorarioErro(LocalDateTime horarioErro) {
		this.horarioErro = horarioErro;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public List<String> getListaErros() {
		return listaErros;
	}


	public void setListaErros(List<String> listaErros) {
		this.listaErros = listaErros;
	}
	
	
	
	
	
	

}
