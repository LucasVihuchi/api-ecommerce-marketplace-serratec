package com.grupo4.projetofinalapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.services.ProdutoService;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {
			
		
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public List<Produto> obterProdutos(){
		return produtoService.obterProdutos();
	}
	
	@GetMapping("{nome}")
	public Produto obterProdutoPorNome(@PathVariable String nome){
		return produtoService.obterProdutoPorNome(nome);
	}
}
