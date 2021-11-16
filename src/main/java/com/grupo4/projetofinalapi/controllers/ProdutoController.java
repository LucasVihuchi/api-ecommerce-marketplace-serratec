package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.ProdutoDTO;
import com.grupo4.projetofinalapi.entities.FotoProduto;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.FotoProdutoInexistenteException;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.FotoProdutoService;
import com.grupo4.projetofinalapi.services.ProdutoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoProdutoService fotoProdutoService;
	
	@GetMapping
	@ApiOperation(value = "Retorna todos os produtos", notes = "Retornar produtos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna todos os produtos")
	})
	public ResponseEntity<List<ProdutoDTO>> obterProdutos(){
		List<Produto> listaProdutos = produtoService.obterProdutos();

		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutosDTO(listaProdutos));
	}
	
	@GetMapping("{nome}")
	@ApiOperation(value = "Retorna os produtos pelo nome", notes = "Retornar produtos por nome")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna os produtos pelo nome")
	})
	public ResponseEntity<List<ProdutoDTO>> obterProdutoPorNome(@PathVariable String nome){
		List<Produto> listaProdutos = produtoService.obterProdutosPorNome(nome);
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutosDTO(listaProdutos));
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Insere um produto", notes = "Inserir produto")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Produto adicionado"),
			@ApiResponse(code = 400, message = "Produto já foi adicionado anteriormente"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário"),
			@ApiResponse(code = 404, message = "Usuário fornecido ou categoria fornecida não constam no sistema"),
			@ApiResponse(code = 500, message = "Ocorreu um erro na manipulação do arquivo da foto")
	})
	public ResponseEntity<ProdutoDTO> inserirProduto(@RequestParam MultipartFile file, @Valid @RequestParam Produto produto, @AuthenticationPrincipal UserDetails usuarioAutenticado) {

		Produto produtoTemp;
		try {
			produtoTemp = produtoService.inserirProduto(produto, file, usuarioAutenticado);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{nome}")
				.buildAndExpand(produto.getNome())
				.toUri();

		return ResponseEntity.created(uri).body(new ProdutoDTO(produtoTemp));
	}

	@GetMapping("{id}/foto")
	@ApiOperation(value = "Retorna foto do produto pelo id", notes = "Retornar foto de produtos por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna foto do produto pelo id"),
			@ApiResponse(code = 404, message = "Foto do produto associada ao id fornecido não consta no sistema")
	})
	public ResponseEntity<byte[]> buscarFotoPorId(@PathVariable Long id) {
		FotoProduto foto = fotoProdutoService.obterFotoPorProdutoId(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", foto.getTipo());
		headers.add("content-length", String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}
}
