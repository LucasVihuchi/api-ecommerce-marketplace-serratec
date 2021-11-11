package com.grupo4.projetofinalapi.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.Sexo;
import com.grupo4.projetofinalapi.exceptions.EnderecoInvalidoException;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
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
		usuarioRecebido.setEndereco(verificaEndereco(usuarioRecebido));
		return usuarioRepository.saveAndFlush(usuarioRecebido);
	}
	
	public void deletarUsuario(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (!usuario.isPresent()) {
			throw new UsuarioInexistenteException("Usuario não existe");
		}
		usuarioRepository.deleteById(id);
	}
	
	@Transactional
	public Usuario atualizarUsuario(Long id, Usuario usuario) {
		Usuario usuarioBD = usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioInexistenteException("Usuario não existe"));
		if (usuario.getNome() != null && usuario.getNome() != "")  {
			usuarioBD.setNome(usuario.getNome());
		}
		if (usuario.getSobrenome() != null && usuario.getSobrenome() != "")  {
			usuarioBD.setSobrenome(usuario.getSobrenome());
		}
		if (usuario.getSexo() == Sexo.M || usuario.getSexo() == Sexo.F)  {
			usuarioBD.setSexo(usuario.getSexo());
		}
		if (usuario.getTelefonePrincipal() != null && usuario.getTelefonePrincipal() != "")  {
			usuarioBD.setTelefonePrincipal(usuario.getTelefonePrincipal());
		}
		if (usuario.getTelefoneSecundario() != null && usuario.getTelefoneSecundario() != "")  {
			usuarioBD.setTelefoneSecundario(usuario.getTelefoneSecundario());
		}
		if (usuario.getSenhaUsuario() != null && usuario.getSenhaUsuario() != "")  {
			usuarioBD.setSenhaUsuario(usuario.getSenhaUsuario());
		}
		if (usuario.getDataNascimento() != null)  {
			usuarioBD.setDataNascimento(usuario.getDataNascimento());
		}
		if (usuario.getEndereco().getCep() != null)  {
			usuarioBD.setEndereco(verificaEndereco(usuario));			
		}
		return usuarioBD;
	}

	public Endereco verificaEndereco(Usuario usuarioRecebido) {
		
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
				return enderecoAtual;
			}
		}
		return usuarioRecebido.getEndereco();
	}

}
