package com.grupo4.projetofinalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo4.projetofinalapi.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	public Produto findProdutoByNome(String nome);
}
