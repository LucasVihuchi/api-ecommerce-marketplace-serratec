package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import com.grupo4.projetofinalapi.entities.Endereco;
import com.grupo4.projetofinalapi.exceptions.EnderecoInexistenteException;
import com.grupo4.projetofinalapi.exceptions.EnderecoInvalidoException;
import com.grupo4.projetofinalapi.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ConsultaCepService consulta;

    @Transactional
    public Endereco atualizarEndereco(Long id, Endereco endereco) {
        Endereco enderecoBD = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoInexistenteException("Endereço associado ao id " + id + " não existe"));

        if(endereco.getCep() != null) {
            endereco = completaEndereco(endereco);
            enderecoBD.setCep(endereco.getCep());
            enderecoBD.setBairro(endereco.getBairro());
            enderecoBD.setCidade(endereco.getCidade());
            enderecoBD.setEstado(endereco.getEstado());
        }
        if(endereco.getComplemento() != null) {
            enderecoBD.setComplemento(endereco.getComplemento());
        }
        if(endereco.getLogradouro() != null) {
            enderecoBD.setLogradouro(endereco.getLogradouro());
        }
        enderecoBD.setNumero(endereco.getNumero());

        return enderecoBD;
    }

    public Endereco completaEndereco(Endereco endereco) {
        if(endereco.getCep() == null || endereco.getCep().length() != 8) {
            throw new EnderecoInvalidoException("CEP '" + endereco.getCep() + "' é inválido");
        }
        ResponseEntity<EnderecoDTO> enderecoValidado = consulta.getEndereco(endereco.getCep());
        if(!enderecoValidado.getStatusCode().equals(HttpStatus.OK) || enderecoValidado.getBody().isErro()) {
            throw new EnderecoInvalidoException("CEP '" + endereco.getCep() + "' é inválido");
        }
        endereco.preencherDadosViaCep(enderecoValidado.getBody());
        return endereco;
    }

}
