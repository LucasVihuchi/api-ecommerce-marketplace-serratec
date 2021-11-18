package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um pedido não existe no banco de dados
 */
public class PedidoInexistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public PedidoInexistenteException(String message) {
		super(message);
	}
}
