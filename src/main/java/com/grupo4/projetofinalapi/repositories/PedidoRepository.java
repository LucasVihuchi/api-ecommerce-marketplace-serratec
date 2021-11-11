package com.grupo4.projetofinalapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grupo4.projetofinalapi.entities.Pedido;



@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
//	@Query(value = "select * from pedido where pedido.cod_comprador = :id")
//	public List<Pedido> findAllByCompradorId(@Param("id") Long id);
//	
//	@Query(value = "select * from pedido where pedido.cod_vendedor = :id")
//	public List<Pedido> findAllByVendedorId(@Param("id") Long id);

	
	
}
