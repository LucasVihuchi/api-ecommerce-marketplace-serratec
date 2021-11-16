package com.grupo4.projetofinalapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo4.projetofinalapi.components.SpringContext;
import com.grupo4.projetofinalapi.entities.Categoria;
import com.grupo4.projetofinalapi.entities.Produto;
import com.grupo4.projetofinalapi.groups.GruposValidacao;
import com.grupo4.projetofinalapi.services.FotoProdutoService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(value = "urlFoto", allowGetters = true)
public class ProdutoDTO {

    @ApiModelProperty(value = "Identificador único do produto")
    private Long id;

    @NotBlank(message = "Nome não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
    @ApiModelProperty(value = "Nome do produto", required = true)
    private String nome;

    @NotBlank(message = "Descrição não pode ficar em branco ou nulo", groups = {GruposValidacao.ValidadorPost.class})
    @ApiModelProperty(value = "Descrição do produto", required = true)
    private String descricao;

    @NotNull(message = "Quantidade em estoque não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
    @PositiveOrZero(message = "Quantidade não pode ser negativa ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
    @ApiModelProperty(value = "Quantidade em estoque do produto", required = true)
    private int qtdEstoque;

    @Past(message = "Data deve ser anterior a hoje", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
    @ApiModelProperty(value = "Data de fabricação do produto")
    private LocalDate dataFabricacao;

    @NotNull(message = "Tempo de garantia não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
    @PositiveOrZero(message = "Tempo de garantia não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
    @ApiModelProperty(value = "Tempo de garantia do produto", required = true)
    private int tempoGarantia;

    @Positive(message = "Preço unitário não pode ser negativo ou zero", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
    @DecimalMax (value = "99999.99", message = "Preço unitário não pode ser superior a R$ {value}", groups = {GruposValidacao.ValidadorPost.class, GruposValidacao.ValidadorPut.class})
    @ApiModelProperty(value = "Preço unitário do produto", required = true)
    private Double precoUnitario;

    @NotNull(message = "Vendedor não pode ser nulo", groups = {GruposValidacao.ValidadorPost.class})
    @ApiModelProperty(value = "Vendedor do produto", required = true)
    private UsuarioDTO vendedor;

    @NotNull(message = "Categoria não pode ser nula", groups = {GruposValidacao.ValidadorPost.class})
    @ApiModelProperty(value = "Categoria do produto", required = true)
    private Categoria categoria;

    @ApiModelProperty(value = "Url da foto do produto", required = true)
    private String urlFoto;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, String descricao, int qtdEstoque, LocalDate dataFabricacao, int tempoGarantia, Double precoUnitario, UsuarioDTO vendedor, Categoria categoria, String urlFoto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.qtdEstoque = qtdEstoque;
        this.dataFabricacao = dataFabricacao;
        this.tempoGarantia = tempoGarantia;
        this.precoUnitario = precoUnitario;
        this.vendedor = vendedor;
        this.categoria = categoria;
        this.urlFoto = urlFoto;
    }

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.qtdEstoque = produto.getQtdEstoque();
        this.dataFabricacao = produto.getDataFabricacao();
        this.tempoGarantia = produto.getTempoGarantia();
        this.precoUnitario = produto.getPrecoUnitario();
        this.vendedor = new UsuarioDTO(produto.getVendedor());
        this.categoria = produto.getCategoria();
        this.urlFoto = gerarStringUrlFoto(this.id);
    }

    public static String gerarStringUrlFoto(long idProduto) {
        return gerarUrlFoto(idProduto);
    }

    public static String gerarUrlFoto(long idProduto) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/produtos/{id}/foto")
                .buildAndExpand(idProduto)
                .toUri();

        return uri.toString();
    }

    public Produto converterParaProduto() {
        FotoProdutoService fotoProdutoService = SpringContext.getBean(FotoProdutoService.class);
        Produto produto = new Produto();
        produto.setId(this.id);
        produto.setNome(this.nome);
        produto.setDescricao(this.descricao);
        produto.setQtdEstoque(this.qtdEstoque);
        produto.setDataFabricacao(this.dataFabricacao);
        produto.setTempoGarantia(this.tempoGarantia);
        if(produto.getVendedor() != null) {
            produto.setVendedor(vendedor.converterParaUsuario());
        }
        produto.setCategoria(this.categoria);
        if(this.id != null) {
            produto.setFoto(fotoProdutoService.obterFotoPorProdutoId(this.id));
        }

        return produto;
    }

    public static List<ProdutoDTO> converterParaListaProdutosDTO(List<Produto> listaProdutos) {
        List<ProdutoDTO> listaProdutosDTO = new ArrayList<>();
        for(Produto produtoAtual : listaProdutos) {
            listaProdutosDTO.add(new ProdutoDTO(produtoAtual));
        }
        return listaProdutosDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public int getTempoGarantia() {
        return tempoGarantia;
    }

    public void setTempoGarantia(int tempoGarantia) {
        this.tempoGarantia = tempoGarantia;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public UsuarioDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioDTO vendedor) {
        this.vendedor = vendedor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDTO that = (ProdutoDTO) o;
        return qtdEstoque == that.qtdEstoque && tempoGarantia == that.tempoGarantia && Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(descricao, that.descricao) && Objects.equals(dataFabricacao, that.dataFabricacao) && Objects.equals(precoUnitario, that.precoUnitario) && Objects.equals(vendedor, that.vendedor) && Objects.equals(categoria, that.categoria) && Objects.equals(urlFoto, that.urlFoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, qtdEstoque, dataFabricacao, tempoGarantia, precoUnitario, vendedor, categoria, urlFoto);
    }
}
