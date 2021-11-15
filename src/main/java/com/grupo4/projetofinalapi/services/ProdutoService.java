package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.entities.FotoProduto;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.CategoriaInexistenteException;
import com.grupo4.projetofinalapi.exceptions.ProdutoExistenteException;
import com.grupo4.projetofinalapi.exceptions.ProdutoInconsistenteException;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.CategoriaRepository;
import com.grupo4.projetofinalapi.repositories.ProdutoRepository;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
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

	public Produto inserirProduto(Produto produto, MultipartFile file, UserDetails usuarioAutenticado) throws IOException {
		validaProdutoPost(produto);

		Usuario vendedor = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));

		List<Produto> listaProdutos = produtoRepository.findAllByNomeIgnoreCase(produto.getNome());
		for(Produto produtoAtual : listaProdutos) {
			boolean vendedorIgual = produtoAtual.getVendedor().getId().equals(vendedor.getId());
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

		if (!vendedor.isEhVendedor()) {
			vendedor.setEhVendedor(true);
			usuarioRepository.saveAndFlush(vendedor);
		}
		produto.setVendedor(vendedor);

		if(produto.getCategoria() == null || produto.getCategoria().getId() == null) {
			throw new CategoriaInexistenteException("Categoria deve ser informada");
		}
		Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
				.orElseThrow(() -> new CategoriaInexistenteException("Categoria associada ao id " + produto.getId() + " não existe"));

		produto.setCategoria(categoria);


		return produtoRepository.saveAndFlush(produto);
	}

	// TODO Melhorar respostas se der tempo
	public void validaProdutoPost(Produto produto) {
		if(
				produto.getNome() == null ||
				produto.getDescricao() == null ||
				produto.getQtdEstoque() <= 0 ||
				produto.getDataFabricacao() == null ||
				produto.getDataFabricacao().isAfter(ChronoLocalDate.from(LocalDateTime.now())) ||
				produto.getTempoGarantia() <= 0 ||
				produto.getPrecoUnitario() <= 0
		) {
			throw new ProdutoInconsistenteException("Produto com campos incorretos");
		}

	}
}
