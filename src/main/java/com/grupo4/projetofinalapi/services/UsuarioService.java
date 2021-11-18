package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.enums.Sexo;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.EnderecoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Classe service realizar a interface entre o controller e repository de usuário
 */
@Service
public class UsuarioService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EnderecoService enderecoService;

	/** Método para inserir um usuário no banco de dados
	 *
	 * @param usuarioRecebido usuário a ser inserido
	 * @return usuário inserido no banco de dados
	 */
	public Usuario inserirUsuario(Usuario usuarioRecebido) {
		usuarioRecebido.setEndereco(enderecoService.completaEndereco(usuarioRecebido.getEndereco()));
		enderecoService.verificaEndereco(usuarioRecebido.getEndereco());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		usuarioRecebido.setSenhaUsuario(passwordEncoder.encode(usuarioRecebido.getSenhaUsuario()));
		return usuarioRepository.saveAndFlush(usuarioRecebido);
	}

	/** Método para deletar um usuário do banco de dados
	 *
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 */
	public void deletarUsuario(UserDetails usuarioAutenticado) {
		Optional<Usuario> usuario = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername());
		if (usuario.isEmpty()) {
			throw new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe");
		}
		usuarioRepository.deleteById(usuario.get().getId());
	}

	/** Método para atualizar os dados de um usuário no banco de dados
	 *
	 * @param usuario usuário com os dados a serem atualizados
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 * @return Usuário com os dados atualizados no banco de dados
	 */
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

	/** Método para obter usuários que tenham email, cpf ou nome de usuário compatíveis com os parâmetros passados
	 *
	 * @param email email a ser pesquisado
	 * @param cpf cpf a ser pesquisado
	 * @param nomeUsuario nome de usuário a ser pesquisado
	 * @return List de usuários com os usuários compatíveis
	 */
	public List<Usuario> obterUsuarioPorEmailCPFOuNomeUsuario(String email, String cpf, String nomeUsuario) {
		return usuarioRepository.findAllByEmailOrCpfOrNomeUsuario(email, cpf, nomeUsuario);
	}

	/** Método para obter a lista de pedidos realizados pelo usuário como comprador
	 *
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 * @return List de pedidos realizados pelo usuário
	 */
	public List<Pedido> obterListaPedidosPorComprador(UserDetails usuarioAutenticado){
		Usuario usuarioBD = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));
		return usuarioBD.getListaPedidosFeitos();
	}

	/** Método para obter a lista de pedidos recebidos pelo usuário como vendedor
	 *
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 * @return List de pedidos recebidos pelo usuário
	 */
	public List<Pedido> obterListaPedidosPorVendedor(UserDetails usuarioAutenticado){
		Usuario usuarioBD = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));
		return usuarioBD.getListaPedidosRecebidos();
	}

}
