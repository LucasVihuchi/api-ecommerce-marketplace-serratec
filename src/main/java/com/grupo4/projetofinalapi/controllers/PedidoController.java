package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.PedidoDTO;
import com.grupo4.projetofinalapi.entities.Pedido;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.ItemPedidoService;
import com.grupo4.projetofinalapi.services.PedidoService;
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

/** Classe controller para comunicação externa por outras aplicações com a entidade pedido
 */
@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ItemPedidoService itemPedidoService;

	/** Método para realizar um pedido de compra
	 *
	 * @param pedidoDTO dados do pedido que será inserido no banco
	 * @param usuarioAutenticado credenciais do usuário que irá realizar o pedido de compra
	 * @return ResponseEntity com os dados do pedido inserido, dos produtos, do comprador e vendedor no corpo da resposta
	 */
	@PostMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Insere um pedido", notes = "Inserir pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Pedido adicionado"),
			@ApiResponse(code = 400, message = "Inconsistência nos dados do pedido, de comprador, de vendedor ou produto"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 404, message = "Comprador, vendedor e/ou produto inexistentes")
	})
	public ResponseEntity<PedidoDTO> realizarPedido(@Validated(GruposValidacao.ValidadorPost.class) @RequestBody PedidoDTO pedidoDTO, @AuthenticationPrincipal UserDetails usuarioAutenticado){
		Pedido pedido = pedidoDTO.converterParaPedido();
		pedido = pedidoService.inserirPedido(pedido, usuarioAutenticado);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/v1/pedidos/{id}")
				.buildAndExpand(pedido.getId())
				.toUri();

		return ResponseEntity.created(uri).body(new PedidoDTO(pedido));
	}

	/** Método para atualizar um pedido de compra ainda não finalizado
	 *
	 * @param id id do pedido que será atualizado
	 * @param pedidoDTO dados do pedido que será inserido no banco
	 * @param usuarioAutenticado credenciais do usuário que irá atualizar o pedido de compra
	 * @return ResponseEntity com os dados do pedido atualizado, dos produtos, do comprador e vendedor no corpo da resposta
	 */
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Atualiza um pedido", notes = "Atualizar pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pedido atualizado"),
			@ApiResponse(code = 400, message = "Inconsistência nos dados do pedido, de comprador ou produto ou pedido já finalizado"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 404, message = "Comprador, pedido e/ou produto inexistentes")
	})
	public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable Long id,
													 @Validated(GruposValidacao.ValidadorPut.class) @RequestBody PedidoDTO pedidoDTO,
													 @AuthenticationPrincipal UserDetails usuarioAutenticado) {

		Pedido pedido = pedidoDTO.converterParaPedido();
		pedido = pedidoService.atualizarPedido(id, pedido, usuarioAutenticado);
		pedido.setListaItemPedido(itemPedidoService.retornarListaItemPedidoPorPedidoId(id));

		return ResponseEntity.ok().body(new PedidoDTO(pedido));
	}

	/** Método para finalizar um pedido
	 *
	 * @param id id do pedido que será finalizado
	 * @param usuarioAutenticado credenciais do usuário que irá finalizar o pedido de compra
	 * @return ResponseEntity com os dados do pedido finalizado, dos produtos, do comprador e vendedor no corpo da resposta
	 */
	@PutMapping("{id}/finalizar")
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Finaliza um pedido", notes = "Finalizar pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Pedido finalizado"),
			@ApiResponse(code = 400, message = "Inconsistência nos dados do pedido, de comprador ou produto ou pedido já finalizado"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 404, message = "Comprador, pedido e/ou produto inexistentes")
	})
	public ResponseEntity<PedidoDTO> finalizarPedido(@PathVariable Long id, @AuthenticationPrincipal UserDetails usuarioAutenticado) {
		Pedido pedido = pedidoService.finalizarPedido(id, usuarioAutenticado);
		return ResponseEntity.ok().body(new PedidoDTO(pedido));
	}
}
