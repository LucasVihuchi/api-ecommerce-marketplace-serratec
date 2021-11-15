package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	@Query(value = "SELECT * FROM produto p WHERE p.nome LIKE CONCAT('%',:nome,'%')", nativeQuery = true)
	List<Produto> findAllAlikeByNomeIgnoreCase(String nome);
}
