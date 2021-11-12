package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.exceptions.ProdutoInexistenteException;
import com.grupo4.projetofinalapi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> obterProdutos(){
		return produtoRepository.findAll();
	}
	
	public Produto obterProdutoPorNome(String nome){
		return produtoRepository.findProdutoByNomeIgnoreCase(nome)
				.orElseThrow(() -> new ProdutoInexistenteException("Produto '" + nome + "' não existe"));
	}
}
