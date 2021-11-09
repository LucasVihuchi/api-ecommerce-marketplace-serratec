package com.grupo4.projetofinalapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.services.CategoriaService;

		
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public List<Categoria> obterCategorias(){
		return categoriaService.obterCategorias();
	}
	
	@GetMapping("{nome}")
	public Categoria obterCategoriaPorNome(@PathVariable String nome){
		return categoriaService.obterCategoriaPorNome(nome);
	}
	
}
