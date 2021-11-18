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

/** Classe service para realizar a interface entre os controllers e o repository de endereço
 */
@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ConsultaCepService consulta;

    /** Método para atualizar os dados de endereço do usuário no banco de dados
     *
     * @param id id do endereço a ser atualizado
     * @param endereco endereco com dados a serem modificados no banco
     * @return Endereco do banco de dados com os dados modificados
     */
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

    /** Método para completar os dados de bairro, cidade e estado de um endereço fornecido
     *
     * @param endereco endereco a ser completado
     * @return Endereco com os dados atualizados
     */
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

    /** Método para verificar se os dados de endereço fornecidos são válidos
     *
     * @param endereco endereco a ser verificado
     */
    public void verificaEndereco(Endereco endereco) {
        if(endereco.getLogradouro() == null || endereco.getLogradouro().equals("")) {
            throw new EnderecoInvalidoException("Logradouro não ficar em branco ou nulo");
        }
        if(endereco.getCep() == null || endereco.getCep().equals("")) {
            throw new EnderecoInvalidoException("CEP não ficar em branco ou nulo");
        }
        if(endereco.getBairro() == null || endereco.getBairro().equals("")) {
            throw new EnderecoInvalidoException("Bairro não ficar em branco ou nulo");
        }
        if(endereco.getCidade() == null || endereco.getCidade().equals("")) {
            throw new EnderecoInvalidoException("Cidade não ficar em branco ou nulo");
        }
        if(endereco.getEstado() == null || endereco.getEstado().equals("")) {
            throw new EnderecoInvalidoException("Estado não ficar em branco ou nulo");
        }
        if(endereco.getNumero() < 0) {
            throw new EnderecoInvalidoException("Número não pode ser negativo");
        }
        if(endereco.getComplemento() != null) {
            if(endereco.getComplemento().equals("")) {
                throw new EnderecoInvalidoException("Complemento não ficar em branco");
            }
        }
    }
}
