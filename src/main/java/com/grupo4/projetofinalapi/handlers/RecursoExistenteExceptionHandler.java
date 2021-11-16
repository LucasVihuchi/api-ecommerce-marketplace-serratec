package com.grupo4.projetofinalapi.handlers;

import com.grupo4.projetofinalapi.entities.ErroRespostaBody;
import com.grupo4.projetofinalapi.exceptions.CategoriaExistenteException;
import com.grupo4.projetofinalapi.exceptions.ProdutoExistenteException;
import com.grupo4.projetofinalapi.exceptions.UsuarioExistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RecursoExistenteExceptionHandler {
	@ExceptionHandler(
			{CategoriaExistenteException.class,
			ProdutoExistenteException.class,
			UsuarioExistenteException.class})
	public ResponseEntity<ErroRespostaBody> handleEnderecoInvalidoException(RuntimeException ex) {
		ErroRespostaBody erroBody = new ErroRespostaBody();
		erroBody.setStatus(HttpStatus.BAD_REQUEST.value());
		erroBody.setTitulo("Recurso já existe no sistema");
		List<String> mensagemErros = new ArrayList<>();
		mensagemErros.add(ex.getMessage());
		erroBody.setListaErros(mensagemErros);
		
		return ResponseEntity.badRequest().body(erroBody);
	}
}
