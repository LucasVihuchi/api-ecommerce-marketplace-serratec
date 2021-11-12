package com.grupo4.projetofinalapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Produto>> obterProdutos(){
		return ResponseEntity.ok().body(produtoService.obterProdutos());
	}
	
	@GetMapping("{nome}")
	public ResponseEntity<Produto> obterProdutoPorNome(@PathVariable String nome){
		return ResponseEntity.ok().body(produtoService.obterProdutoPorNome(nome));
	}
}
