package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.exceptions.CategoriaExistenteException;
import com.grupo4.projetofinalapi.exceptions.CategoriaInexistenteException;
import com.grupo4.projetofinalapi.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Classe service realizar a interface entre o controller e repository de categoria
 */
@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	/** Método para retornar todas as categorias presentes no banco de dados
	 *
	 * @return List de todas as categorias presentes no banco de dados
	 */
	public List<Categoria> obterCategorias(){
		return categoriaRepository.findAll();
	}

	/** Método para retornar uma categoria pelo seu nome
	 *
	 * @param nome nome da categoria a ser retornada
	 * @return Categoria correspondente ao nome fornecido
	 */
	public Categoria obterCategoriaPorNome(String nome){
		return categoriaRepository.findCategoriaByNomeIgnoreCase(nome)
				.orElseThrow(() -> new CategoriaInexistenteException("Categoria '" + nome + "' não existe"));
	}

	/** Método para inserir uma categoria no banco de dados
	 *
	 * @param categoria categoria a ser adicionada no banco de dados
	 * @return Categoria que foi inserida no banco de dados
	 */
	public Categoria inserirCategoria(Categoria categoria) {
		Optional<Categoria> categoriaBD = categoriaRepository.findCategoriaByNomeIgnoreCase(categoria.getNome());
		if (categoriaBD.isPresent()) {
			throw new CategoriaExistenteException("Categoria '" + categoriaBD.get().getNome() + "' já existe");
		}
		return categoriaRepository.saveAndFlush(categoria);
	}
}
