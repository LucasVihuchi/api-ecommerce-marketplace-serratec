package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que há uma inconsistência no pedido
 */
public class PedidoInconsistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public PedidoInconsistenteException(String message) {
		super(message);
	}
}
