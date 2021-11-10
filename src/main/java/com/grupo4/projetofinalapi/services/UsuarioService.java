package com.grupo4.projetofinalapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.EnderecoInvalidoException;
import com.grupo4.projetofinalapi.repositories.EnderecoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ConsultaCepService consulta;
	
	
	public Usuario inserirUsuario(Usuario usuarioRecebido){
		if(usuarioRecebido.getEndereco().getCep().length() != 8) {
			throw new EnderecoInvalidoException("CEP inválido");
		}
		ResponseEntity<EnderecoDTO> enderecoValidado = consulta.getEndereco(usuarioRecebido.getEndereco().getCep());
		if(!Endereco.enderecoEhValido(usuarioRecebido.getEndereco(), enderecoValidado.getBody())) {
			throw new EnderecoInvalidoException("Dados de endereço invalido");
		}
		List<Endereco> listaEnderecosBD = enderecoRepository.findAllByCep(usuarioRecebido.getEndereco().getCep());
		for(Endereco enderecoAtual : listaEnderecosBD) {
			if(enderecoAtual.equals(usuarioRecebido.getEndereco())) {
				usuarioRecebido.setEndereco(enderecoAtual);
				break;
			}
		}
		return usuarioRepository.saveAndFlush(usuarioRecebido);
	}
	
}
