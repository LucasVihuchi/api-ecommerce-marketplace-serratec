package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
//	@Query(value = "select * from pedido where pedido.cod_comprador = :id")
//	public List<Pedido> findAllByCompradorId(@Param("id") Long id);
//	
//	@Query(value = "select * from pedido where pedido.cod_vendedor = :id")
//	public List<Pedido> findAllByVendedorId(@Param("id") Long id);
}
