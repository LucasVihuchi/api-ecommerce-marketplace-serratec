package com.grupo4.projetofinalapi.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.grupo4.projetofinalapi.entities.ErroRespostaBody;


@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler{

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
