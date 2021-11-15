package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{
    @Query(value = "SELECT * FROM item_pedido ip WHERE ip.cod_pedido = :id", nativeQuery = true)
    List<ItemPedido> findAllByPedidoId(Long id);

    @Modifying
    @Query(value = "DELETE FROM item_pedido ip WHERE ip.cod_item_pedido = :id", nativeQuery = true)
    void deletaItemPedidoById(Long id);
}
