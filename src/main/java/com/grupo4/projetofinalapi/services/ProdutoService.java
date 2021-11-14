package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.entities.FotoProduto;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.CategoriaInexistenteException;
import com.grupo4.projetofinalapi.exceptions.ProdutoExistenteException;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.CategoriaRepository;
import com.grupo4.projetofinalapi.repositories.ProdutoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Produto> obterProdutos(){
		return produtoRepository.findAll();
	}
	
	public List<Produto> obterProdutosPorNome(String nome){
		return produtoRepository.findAllByNomeIgnoreCase(nome);
	}

	public Produto inserirProduto(Produto produto, MultipartFile file) throws IOException {
		List<Produto> listaProdutos = produtoRepository.findAllByNomeIgnoreCase(produto.getNome());
		for(Produto produtoAtual : listaProdutos) {
			boolean vendedorIgual = produtoAtual.getVendedor().getId().equals(produto.getVendedor().getId());
			boolean nomeProdutoIgual = produtoAtual.getNome().equalsIgnoreCase(produto.getNome());
			if(vendedorIgual && nomeProdutoIgual) {
				throw new ProdutoExistenteException("Produto com o nome '" + produto.getNome() + "' já cadastrado para este vendedor");
			}
		}

		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setDados(file.getBytes());
		fotoProduto.setTipo(file.getContentType());
		fotoProduto.setProduto(produto);

		produto.setFoto(fotoProduto);

		Usuario vendedor = usuarioRepository.findById(produto.getVendedor().getId())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao id " + produto.getId() + " não existe"));

		if (!vendedor.isEhVendedor()) {
			vendedor.setEhVendedor(true);
		}
		usuarioRepository.saveAndFlush(vendedor);
		produto.setVendedor(vendedor);

		Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
				.orElseThrow(() -> new CategoriaInexistenteException("Categoria associada ao id " + produto.getId() + " não existe"));

		produto.setCategoria(categoria);


		return produtoRepository.saveAndFlush(produto);
	}
}
