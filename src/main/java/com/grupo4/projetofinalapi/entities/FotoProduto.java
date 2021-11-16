package com.grupo4.projetofinalapi.entities;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class FotoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_foto", nullable = false, columnDefinition = "serial")
    @ApiModelProperty(value = "Identificador único da foto do produto")
    private Long id;

    @Lob
    @Column(columnDefinition = "bytea", nullable = false)
    @Type(type = "org.hibernate.type.BinaryType")
    @ApiModelProperty(value = "Dados da foto", required = true)
    private byte[] dados;

    @Column(length = 25, nullable = false)
    @ApiModelProperty(value = "Tipo de arquivo da foto", required = true)
    private String tipo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_produto", columnDefinition = "int4")
    @ApiModelProperty(value = "Produto a qual a foto pertence", required = true)
    private Produto produto;

    public FotoProduto() {
    }

    public FotoProduto(Long id, byte[] dados, String tipo, Produto produto) {
        this.id = id;
        this.dados = dados;
        this.tipo = tipo;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FotoProduto that = (FotoProduto) o;
        return Objects.equals(id, that.id) && Arrays.equals(dados, that.dados) && Objects.equals(tipo, that.tipo) && Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, tipo, produto);
        result = 31 * result + Arrays.hashCode(dados);
        return result;
    }
}
