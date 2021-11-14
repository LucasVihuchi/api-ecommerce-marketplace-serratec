package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.FotoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoProdutoRepository extends JpaRepository<FotoProduto, Long>{
    @Query(value = "SELECT * FROM foto_produto fp WHERE fp.cod_produto = :id", nativeQuery = true)
    FotoProduto findByProdutoId(Long id);
}
