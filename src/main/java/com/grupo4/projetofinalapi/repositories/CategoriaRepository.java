package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	Optional<Categoria> findCategoriaByNomeIgnoreCase(String nome);
}
