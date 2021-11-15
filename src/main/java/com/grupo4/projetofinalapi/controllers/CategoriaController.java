package com.grupo4.projetofinalapi.controllers;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> obterCategorias(){
		return ResponseEntity.ok().body(categoriaService.obterCategorias());
	}
	
	@GetMapping("{nome}")
	public ResponseEntity<Categoria> obterCategoriaPorNome(@PathVariable String nome){
		return ResponseEntity.ok().body(categoriaService.obterCategoriaPorNome(nome));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_usuario')")
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
