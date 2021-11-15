package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	// TODO Procurar produto por palavra chave
	List<Produto> findAllByNomeIgnoreCase(String nome);
}
