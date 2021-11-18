package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Interface repositório para interação com a entidade produto
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	/** Método para pesquisar todos os produtos que contenham parcialmente ou totalmente a string fornecida ;
	 *
	 * @param nome nome a ser pesquisado
	 * @return List de produtos com os produtos correspondentes
	 */
	List<Produto> findAllByNomeContaining(String nome);
}
