package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que um produto não existe no banco de dados
 */
public class ProdutoInexistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public ProdutoInexistenteException(String message) {
		super(message);
	}
}
