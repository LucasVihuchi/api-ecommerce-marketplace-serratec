package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.Sexo;
import com.grupo4.projetofinalapi.exceptions.EnderecoInvalidoException;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.EnderecoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ConsultaCepService consulta;
	
	
	public Usuario inserirUsuario(Usuario usuarioRecebido){
		usuarioRecebido.setEndereco(verificaEndereco(usuarioRecebido.getEndereco()));
		return usuarioRepository.saveAndFlush(usuarioRecebido);
	}
	
	public void deletarUsuario(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty()) {
			throw new UsuarioInexistenteException("Usuário associado ao id " + id + " não existe");
		}
		usuarioRepository.deleteById(id);
	}
	
	@Transactional
	public Usuario atualizarUsuario(Long id, Usuario usuario) {
		Usuario usuarioBD = usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao id " + id + " não existe"));

		if (usuario.getNome() != null && !Objects.equals(usuario.getNome(), ""))  {
			usuarioBD.setNome(usuario.getNome());
		}
		if (usuario.getSobrenome() != null && !Objects.equals(usuario.getSobrenome(), ""))  {
			usuarioBD.setSobrenome(usuario.getSobrenome());
		}
		if (usuario.getSexo() == Sexo.M || usuario.getSexo() == Sexo.F)  {
			usuarioBD.setSexo(usuario.getSexo());
		}
		if (usuario.getTelefonePrincipal() != null && !Objects.equals(usuario.getTelefonePrincipal(), ""))  {
			usuarioBD.setTelefonePrincipal(usuario.getTelefonePrincipal());
		}
		if (usuario.getTelefoneSecundario() != null && !Objects.equals(usuario.getTelefoneSecundario(), ""))  {
			usuarioBD.setTelefoneSecundario(usuario.getTelefoneSecundario());
		}
		if (usuario.getSenhaUsuario() != null && !Objects.equals(usuario.getSenhaUsuario(), ""))  {
			usuarioBD.setSenhaUsuario(usuario.getSenhaUsuario());
		}
		if (usuario.getDataNascimento() != null)  {
			usuarioBD.setDataNascimento(usuario.getDataNascimento());
		}
		if (usuario.getEndereco().getCep() != null)  {
			usuarioBD.setEndereco(verificaEndereco(usuario.getEndereco()));
		}
		return usuarioBD;
	}

	public Endereco verificaEndereco(Endereco endereco) {
		if(endereco.getCep().length() != 8) {
			throw new EnderecoInvalidoException("CEP '" + endereco.getCep() + "' é inválido");
		}
		ResponseEntity<EnderecoDTO> enderecoValidado = consulta.getEndereco(endereco.getCep());
		if((enderecoValidado.getBody() == null) || (!Endereco.enderecoEhValido(endereco, enderecoValidado.getBody()))) {
			throw new EnderecoInvalidoException("Dados de endereço inválidos");
		}
		return endereco;
	}

	public List<Pedido> obterListaPedidosPorComprador(Long id){
		Usuario usuarioBD = usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao id " + id + " não existe"));
		return usuarioBD.getListaPedidosFeitos();
	}

	public List<Pedido> obterListaPedidosPorVendedor(Long id){
		Usuario usuarioBD = usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao id " + id + " não existe"));
		return usuarioBD.getListaPedidosRecebidos();
	}

}
