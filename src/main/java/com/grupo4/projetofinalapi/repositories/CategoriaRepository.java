package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** Interface repositório para interação com a entidade categoria
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	/** Método para pesquisar uma categoria pelo nome exato
	 *
	 * @param nome nome da categoria a ser retornada
	 * @return Um optional de categoria caso verdadeiro. Caso falso, nulo
	 */
	Optional<Categoria> findCategoriaByNomeIgnoreCase(String nome);
}
