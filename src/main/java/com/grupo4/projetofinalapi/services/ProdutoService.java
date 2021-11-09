package com.grupo4.projetofinalapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> obterProdutos(){
		return produtoRepository.findAll();
	}
	
	public Produto obterProdutoPorNome(String nome){
		return produtoRepository.findProdutoByNome(nome);
	}
}
