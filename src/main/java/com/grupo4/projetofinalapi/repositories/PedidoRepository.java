package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    @Query(value = "SELECT last_value + 1 FROM pedido_cod_pedido_seq", nativeQuery = true)
    long getNextValMySequence();

}
