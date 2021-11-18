package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um item do pedido não existe no banco de dados
 */
public class ItemPedidoInexistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public ItemPedidoInexistenteException(String message) {
		super(message);
	}
}
