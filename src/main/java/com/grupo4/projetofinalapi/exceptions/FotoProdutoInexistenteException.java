package com.grupo4.projetofinalapi.exceptions;

/** Lançada para indicar que uma foto do produto não existe no banco de dados
 */
public class FotoProdutoInexistenteException extends RuntimeException{

	/** Construtor que recebe uma mensagem que pode ser recuperada posteriormente pelo método getMessage()
	 *
	 * @param message a mensagem fornecida
	 */
	public FotoProdutoInexistenteException(String message) {
		super(message);
	}
}
