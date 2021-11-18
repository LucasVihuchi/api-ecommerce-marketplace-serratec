package com.grupo4.projetofinalapi.handlers;

import com.grupo4.projetofinalapi.entities.ErroRespostaBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/** Classe para capturar exceções do tipo MethodArgumentNotValidException e fornecer tratamento customizado
 */
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler{

	/** Método para capturar exceções do tipo MethodArgumentNotValidException e fornecer tratamento customizado
	 *
	 * @param ex exceção que foi capturada pelo handler
	 * @return ResponseEntity com o detalhamento da exceção no corpo da resposta
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErroRespostaBody erroBody = new ErroRespostaBody();
		erroBody.setStatus(status.value());
		erroBody.setTitulo("Ocorreu um erro na validação dos campos");
		
		List<String> mensagensErro = new ArrayList<>();
		for(FieldError erroAtual : ex.getBindingResult().getFieldErrors()) {
			mensagensErro.add(erroAtual.getField() + ": " + erroAtual.getDefaultMessage());
		}
		erroBody.setListaErros(mensagensErro);
		
		return super.handleExceptionInternal(ex, erroBody, headers, status, request);
	}
}
