package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.FotoProduto;
import com.grupo4.projetofinalapi.exceptions.FotoProdutoInexistenteException;
import com.grupo4.projetofinalapi.repositories.FotoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoProdutoService {

    @Autowired
    private FotoProdutoRepository fotoProdutoRepository;

    public FotoProduto obterFotoPorProdutoId(Long id) {
        FotoProduto fotoProduto = fotoProdutoRepository.findByProdutoId(id);

        if (fotoProduto == null) {
            throw new FotoProdutoInexistenteException("Foto do produto de id " + id + " não existe");
        }

        return fotoProduto;
    }

}
