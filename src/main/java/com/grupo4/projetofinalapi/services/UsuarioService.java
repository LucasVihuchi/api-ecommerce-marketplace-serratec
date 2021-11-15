package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.Sexo;
import com.grupo4.projetofinalapi.exceptions.EnderecoInexistenteException;
import com.grupo4.projetofinalapi.exceptions.EnderecoInvalidoException;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.EnderecoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private EnderecoService enderecoService;
	
	
	public Usuario inserirUsuario(Usuario usuarioRecebido){
		usuarioRecebido.setEndereco(enderecoService.completaEndereco(usuarioRecebido.getEndereco()));
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		usuarioRecebido.setSenhaUsuario(passwordEncoder.encode(usuarioRecebido.getSenhaUsuario()));
		return usuarioRepository.saveAndFlush(usuarioRecebido);
	}
	
	public void deletarUsuario(UserDetails usuarioAutenticado) {
		Optional<Usuario> usuario = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername());
		if (usuario.isEmpty()) {
			throw new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe");
		}
		usuarioRepository.deleteById(usuario.get().getId());
	}
	
	@Transactional
	public Usuario atualizarUsuario(Usuario usuario, UserDetails usuarioAutenticado) {
		Usuario usuarioBD = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));

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
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			usuarioBD.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));
		}
		if (usuario.getDataNascimento() != null)  {
			usuarioBD.setDataNascimento(usuario.getDataNascimento());
		}
		if (usuario.getEndereco() != null)  {
			enderecoService.atualizarEndereco(usuarioBD.getEndereco().getId(), usuario.getEndereco());
		}
		return usuarioBD;
	}

	public List<Pedido> obterListaPedidosPorComprador(UserDetails usuarioAutenticado){
		Usuario usuarioBD = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));
		return usuarioBD.getListaPedidosFeitos();
	}

	public List<Pedido> obterListaPedidosPorVendedor(UserDetails usuarioAutenticado){
		Usuario usuarioBD = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));
		return usuarioBD.getListaPedidosRecebidos();
	}

}
