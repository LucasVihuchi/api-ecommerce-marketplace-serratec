package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.exceptions.CategoriaExistenteException;
import com.grupo4.projetofinalapi.exceptions.CategoriaInexistenteException;
import com.grupo4.projetofinalapi.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> obterCategorias(){
		return categoriaRepository.findAll();
	}
	
	public Categoria obterCategoriaPorNome(String nome){
		return categoriaRepository.findCategoriaByNomeIgnoreCase(nome)
				.orElseThrow(() -> new CategoriaInexistenteException("Categoria '" + nome + "' não existe"));
	}
	
	public Categoria inserirCategoria(Categoria categoria) {
		Optional<Categoria> categoriaBD = categoriaRepository.findCategoriaByNomeIgnoreCase(categoria.getNome());
		if (categoriaBD.isPresent()) {
			throw new CategoriaExistenteException("Categoria '" + categoriaBD.get().getNome() + "' já existe");
		}
		return categoriaRepository.saveAndFlush(categoria);
	}
}
