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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

/** Classe service realizar a interface entre o controller e repository de produto
 */
@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	/** Método para obter todos os produtos do banco de dados
	 *
	 * @return List com todos os produtos no banco de dados
	 */
	public List<Produto> obterProdutos(){
		return produtoRepository.findAll();
	}

	/** Método para obter todos os produtos que correspondem parcialmente ou totalmente ao nome fornecido
	 *
	 * @param nome nome ou parte do nome do produto
	 * @return List com os produtos compatíveis no banco
	 */
	public List<Produto> obterProdutosPorNome(String nome){
		return produtoRepository.findAllByNomeContaining(nome);
	}

	/** Método para inserir um produto e sua foto do produto no banco de dados
	 *
	 * @param produto produto a ser inserido
	 * @param file arquivo da foto a ser inserido
	 * @param usuarioAutenticado credenciais do usuário autenticado
	 * @return Produto inserido no banco de dados
	 * @throws IOException caso ocorra um erro na manipulação do arquivo da foto
	 */
	public Produto inserirProduto(Produto produto, MultipartFile file, UserDetails usuarioAutenticado) throws IOException {
		validaProdutoPost(produto);

		Usuario vendedor = usuarioRepository.findByNomeUsuario(usuarioAutenticado.getUsername())
				.orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + usuarioAutenticado.getUsername() + "' não existe"));

		List<Produto> listaProdutos = produtoRepository.findAllByNomeContaining(produto.getNome());
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

	/** Método para validar o produto inserido. Esse método é necessário, pois as validações do Spring não são compatíveis com request param
	 *
	 * @param produto produto a ser validado
	 */
	public void validaProdutoPost(Produto produto) {
		if(produto.getNome() == null || produto.getNome().equals("")) {
			throw new ProdutoInconsistenteException("Nome não pode ficar em branco ou ser nulo");
		}
		if(produto.getDescricao() == null || produto.getDescricao().equals("")) {
			throw new ProdutoInconsistenteException("Descrição não pode ficar em branco ou ser nulo");
		}
		if(produto.getQtdEstoque() <= 0) {
			throw new ProdutoInconsistenteException("Quantidade em estoque deve ser positiva");
		}
		if(produto.getDataFabricacao() != null) {
			if(produto.getDataFabricacao().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
				throw new ProdutoInconsistenteException("Data de fabricação deve ");
			}
		}
		if(produto.getTempoGarantia() <= 0) {
			throw new ProdutoInconsistenteException("Tempo de garantia deve ser positivo");
		}
		if(produto.getPrecoUnitario() <= 0 || produto.getPrecoUnitario() > 99999.99) {
			throw new ProdutoInconsistenteException("Preço unitário deve ser positivo e menor que R$ 100.000,00");
		}
		if(produto.getCategoria() == null) {
			throw new ProdutoInconsistenteException("Produto deve conter uma categoria");
		}
	}
}
