package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.CategoriaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/** Classe controller para comunicação externa por outras aplicações com a entidade categoria
 */
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	/** Método para retornar todas as categorias presentes no banco de dados
	 *
	 * @return ResponseEntity com List de categorias presentes no banco de dados no corpo da resposta
	 */
	@GetMapping
	@ApiOperation(value = "Retorna todas as categorias", notes = "Retornar categorias")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna todas as categorias")
	})
	public ResponseEntity<List<Categoria>> obterCategorias(){
		return ResponseEntity.ok().body(categoriaService.obterCategorias());
	}

	/** Método para retornar uma categoria pelo seu nome
	 *
	 * @param nome nome da categoria a ser retornada
	 * @return ResponseEntity com categoria correspondente ao nome fornecido no corpo da resposta
	 */
	@GetMapping("{nome}")
	@ApiOperation(value = "Retorna as categorias pelo nome", notes = "Retornar categorias por nome")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna as categorias pelo nome"),
			@ApiResponse(code = 404, message = "Nenhuma categoria encontrada com o nome fornecido")
	})
	public ResponseEntity<Categoria> obterCategoriaPorNome(@PathVariable String nome){
		return ResponseEntity.ok().body(categoriaService.obterCategoriaPorNome(nome));
	}

	/** Método para inserir uma categoria no banco de dados
	 *
	 * @param categoria categoria a ser adicionada no banco de dados
	 * @return ReponseEntity com categoria que foi inserida no banco de dados
	 */
	@PostMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
	@ApiOperation(value = "Insere uma categoria", notes = "Inserir categoria")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Categoria adicionada"),
			@ApiResponse(code = 400, message = "Categoria já foi adicionada anteriormente"),
			@ApiResponse(code = 401, message = "Falha na autenticação do usuário")
	})
	public ResponseEntity<Object> inserirCategoria(@Validated(GruposValidacao.ValidadorPost.class) @RequestBody Categoria categoria){
		categoria = categoriaService.inserirCategoria(categoria);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId())
				.toUri();

		return ResponseEntity.created(uri).body(categoria);
	}
}
