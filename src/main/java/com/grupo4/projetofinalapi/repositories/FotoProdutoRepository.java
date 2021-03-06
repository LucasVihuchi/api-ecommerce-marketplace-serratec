package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.FotoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** Interface repositório para interação com a entidade foto_produto
 */
@Repository
public interface FotoProdutoRepository extends JpaRepository<FotoProduto, Long>{

    /** Método para pesquisar uma foto de produto baseado no id do produto
     *
     * @param id id do produto
     * @return Uma foto do produto caso verdadeiro. Caso falso, nulo
     */
    @Query(value = "SELECT * FROM foto_produto fp WHERE fp.cod_produto = :id", nativeQuery = true)
    FotoProduto findByProdutoId(Long id);
}
