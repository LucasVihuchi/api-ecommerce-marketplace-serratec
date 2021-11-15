package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.dto.ProdutoDTO;
import com.grupo4.projetofinalapi.entities.FotoProduto;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.exceptions.FotoProdutoInexistenteException;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.FotoProdutoService;
import com.grupo4.projetofinalapi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<ProdutoDTO>> obterProdutos(){
		List<Produto> listaProdutos = produtoService.obterProdutos();

		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutosDTO(listaProdutos));
	}
	
	@GetMapping("{nome}")
	public ResponseEntity<List<ProdutoDTO>> obterProdutoPorNome(@PathVariable String nome){
		List<Produto> listaProdutos = produtoService.obterProdutosPorNome(nome);
		return ResponseEntity.ok().body(ProdutoDTO.converterParaListaProdutosDTO(listaProdutos));
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> inserirProduto(@RequestParam MultipartFile file, @Valid @RequestParam Produto produto) {
		Produto produtoTemp;
		try {
			produtoTemp = produtoService.inserirProduto(produto, file);
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
	public ResponseEntity<byte[]> buscarFotoPorId(@PathVariable Long id) {
		FotoProduto foto = fotoProdutoService.obterFotoPorProdutoId(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", foto.getTipo());
		headers.add("content-length", String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}
}
