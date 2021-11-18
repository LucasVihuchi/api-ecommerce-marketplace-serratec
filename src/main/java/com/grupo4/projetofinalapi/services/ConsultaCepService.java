package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/** Classe para consulta de CEP no ViaCEP
 */
@Service
public class ConsultaCepService {

    /** Método para realizar consulta de endereço na API do ViaCEP
     *
     * @param cep cep a ser consultado
     * @return EnderecoDTO com os dados recebidos da consulta
     */
    public ResponseEntity<EnderecoDTO> getEndereco(String cep) {
        
        WebClient wc = WebClient.create("https://viacep.com.br/");
        
        ResponseEntity<EnderecoDTO> endereco = wc
        .get()
        .uri(b -> b.path("/ws/{cep}/json/").build(cep))
        .retrieve()
        .toEntity(EnderecoDTO.class)
        .block();
        
        return endereco;
    }
}
