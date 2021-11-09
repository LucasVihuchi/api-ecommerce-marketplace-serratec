package com.grupo4.projetofinalapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> obterCategorias(){
		return categoriaRepository.findAll();
	}
	
	public Categoria obterCategoriaPorNome(String nome){
		return categoriaRepository.findCategoriaByNome(nome);
	}
}
