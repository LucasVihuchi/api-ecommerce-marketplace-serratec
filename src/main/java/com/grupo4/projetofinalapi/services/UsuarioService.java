package com.grupo4.projetofinalapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.entities.Usuario;

@Service
public class UsuarioService {
//TODO TERMINAR O POST DE USUARIO	
	
//	@Autowired
//	private ConsultaCepService consulta;
//	
//	public ResponseEntity <Object> inserirUsuario(Usuario usuarioRecebido){
//		ResponseEntity<EnderecoDTO> enderecoValidado = consulta.getEndereco(usuarioRecebido.getEndereco().getCep());
//		if(enderecoValidado.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
//			return ResponseEntity.badRequest().body("cep invalido");
//		}
//		if(!Endereco.enderecoEhValido(usuarioRecebido.getEndereco(), enderecoValidado.getBody())) {
//			return ResponseEntity.badRequest().body("Dados de endereço invalido");
//		}
//		
//	}
	
}
