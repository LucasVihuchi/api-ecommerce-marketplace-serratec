package com.grupo4.projetofinalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.projetofinalapi.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
