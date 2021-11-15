package com.grupo4.projetofinalapi.handlers;

import com.grupo4.projetofinalapi.entities.ErroRespostaBody;
import com.grupo4.projetofinalapi.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RecursoInexistenteExceptionHandler {
	@ExceptionHandler(
			{CategoriaInexistenteException.class,
			EnderecoInexistenteException.class,
			FotoProdutoInexistenteException.class,
			ItemPedidoInexistenteException.class,
			PedidoInexistenteException.class,
			ProdutoInexistenteException.class,
			UsuarioInexistenteException.class})
	public ResponseEntity<ErroRespostaBody> handleEnderecoInvalidoException(RuntimeException ex) {
		ErroRespostaBody erroBody = new ErroRespostaBody();
		erroBody.setStatus(HttpStatus.NOT_FOUND.value());
		erroBody.setTitulo("Ocorreu um erro na busca de um recurso");
		List<String> mensagemErros = new ArrayList<>();
		mensagemErros.add(ex.getMessage());
		erroBody.setListaErros(mensagemErros);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroBody);
	}
}
