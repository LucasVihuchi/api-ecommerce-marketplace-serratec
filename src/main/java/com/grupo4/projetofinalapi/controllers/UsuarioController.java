package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.dto.UsuarioDTO;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.UsuarioExistenteException;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.PedidoService;
import com.grupo4.projetofinalapi.services.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/** Classe controller para comunicação externa por outras aplicações com a entidade usuário
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PedidoService pedidoService;

	/** Método para inserir um usuário no banco
	 *
	 * @param usuarioDTO usuário a ser inserido no banco de dados
	 * @return ResponseEntity com usuário inserido no banco de dados no corpo da resposta
	 */
	@PostMapping
	@ApiOperation(value = "Insere um usuário", notes = "Inserir usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Usuário adicionado"),
			@ApiResponse(code = 400, message = "Inconsistência nos dados de usuário e endereço ou usuário já existe"),
	})
	public ResponseEntity<Object> criarUsuario(@Validated(GruposValidacao.ValidadorPost.class) @RequestBody UsuarioDTO usuarioDTO){

		List<Usuario> listaUsuariosBD = usuarioService.obterUsuarioPorEmailCPFOuNomeUsuario(usuarioDTO.getEmail(), usuarioDTO.getCpf(), usuarioDTO.getNomeUsuario());
		if(listaUsuariosBD.size() != 0) {
			throw new UsuarioExistenteException("Email, CPF e/ou nome de usuário já está existe no sistema");
		}

		Usuario usuario = usuarioDTO.converterParaUsuario();
		usuario = usuarioService.inserirUsuario(usuario);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
	}

	/** Método para deletar usuário cadastrado no banco de dados
	 *
	 * @param usuarioAutenticado credenciais do usuário que será deletado
	 * @return ResponseEntity com status code 200 informando que foi deletado com sucesso
	 */
	@DeleteMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Deleta um usuário", notes = "Deletar usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário deletado"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 400, message = "Usuário fornecido não consta no sistema")
	})
	public ResponseEntity<?> deletarUsuario(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
		usuarioService.deletarUsuario(usuarioAutenticado);
		return ResponseEntity.ok().build();
	}

	/** Método para atualizar dados do usuário no banco de dados
	 *
	 * @param usuarioDTO usuário com os dados a serem alterados
	 * @param usuarioAutenticado credenciais do usuário que terá seus dados atualizados
	 * @return ResponseEntity com o usuário atualizado no banco de dados no corpo da resposta
	 */
	@PutMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Atualiza um usuário", notes = "Atualizar usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário atualizado"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 404, message = "Usuário fornecido não consta no sistema ou endereço não encontrado")
	})
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@Validated(GruposValidacao.ValidadorPut.class) @RequestBody UsuarioDTO usuarioDTO, @AuthenticationPrincipal UserDetails usuarioAutenticado) {
		Usuario usuario = usuarioDTO.converterParaUsuario();
		usuario = usuarioService.atualizarUsuario(usuario, usuarioAutenticado);
		
		return ResponseEntity.ok().body(new UsuarioDTO(usuario));
	}

	/** Método para obter a lista de pedidos realizados por um usuário
	 *
	 * @param usuarioAutenticado credenciais do usuário que terá seus pedidos retornados
	 * @return ResponseEntity com List de pedidos realizados pelo usuário no corpo da resposta
	 */
	@GetMapping("compras")
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Retorna todos os pedidos de compras do usuário", notes = "Retornar pedidos de compras do usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna todos os pedidos de compras do usuário"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 404, message = "Usuário fornecido não consta no sistema")
	})
	public ResponseEntity<List<PedidoDTO>> obterListaPedidosPorComprador(@AuthenticationPrincipal UserDetails usuarioAutenticado){
		List<PedidoDTO> listaPedidosDTO = PedidoDTO.converterParaPedidosDTO(usuarioService.obterListaPedidosPorComprador(usuarioAutenticado));
		return ResponseEntity.ok().body(listaPedidosDTO);
	}

	/** Método para obter a lista de pedidos recebidos por um usuário
	 *
	 * @param usuarioAutenticado credenciais do usuário que terá seus pedidos retornados
	 * @return ResponseEntity com List de pedidos recebidos pelo usuário no corpo da resposta
	 */
	@GetMapping("vendas")
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Retorna todos os pedidos de vendas do usuário", notes = "Retornar pedidos de vendas do usuário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna todos os pedidos de vendas do usuário"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 404, message = "Usuário fornecido não consta no sistema")
	})
	public ResponseEntity<List<PedidoDTO>> obterListaPedidosPorVendedor(@AuthenticationPrincipal UserDetails usuarioAutenticado){
		List<PedidoDTO> listaPedidosDTO = PedidoDTO.converterParaPedidosDTO(usuarioService.obterListaPedidosPorVendedor(usuarioAutenticado));
		return ResponseEntity.ok().body(listaPedidosDTO);
	}
}
