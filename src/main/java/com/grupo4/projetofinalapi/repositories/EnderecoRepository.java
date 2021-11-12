package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	List<Endereco> findAllByCep(String cep);
}
