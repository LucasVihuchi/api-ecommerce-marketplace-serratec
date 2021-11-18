package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um pedido já foi finalizado no banco de dados
 */
public class PedidoJaFinalizadoException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public PedidoJaFinalizadoException(String message) {
		super(message);
	}
}
