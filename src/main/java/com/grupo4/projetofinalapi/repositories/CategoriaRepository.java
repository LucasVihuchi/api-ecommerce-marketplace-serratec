package com.grupo4.projetofinalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo4.projetofinalapi.entities.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	public Categoria findCategoriaByNome(String nome);
}
