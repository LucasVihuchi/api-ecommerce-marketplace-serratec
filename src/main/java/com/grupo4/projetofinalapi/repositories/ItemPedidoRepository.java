package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Interface repositório para interação com a entidade item_pedido
 */
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{

    /** Método para pesquisar todos os items de pedidos que tenha um id de pedido específico.
     *
     * @param id id do pedido
     * @return List de itemPedido com os pedidos correspondentes
     */
    @Query(value = "SELECT * FROM item_pedido ip WHERE ip.cod_pedido = :id", nativeQuery = true)
    List<ItemPedido> findAllByPedidoId(Long id);

    /** Método para deletar um item de pedido pelo id do pedido
     *
     * @param id id do pedido;
     */
    @Modifying
    @Query(value = "DELETE FROM item_pedido ip WHERE ip.cod_item_pedido = :id", nativeQuery = true)
    void deletaItemPedidoById(Long id);
}
