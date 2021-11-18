package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um produto já existe no banco de dados
 */
public class ProdutoExistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public ProdutoExistenteException(String message) {
		super(message);
	}
}
