package com.grupo4.projetofinalapi.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo4.projetofinalapi.entities.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

	public List<Endereco> findAllByCep(String cep);

}
