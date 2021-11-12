package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.dto.EnderecoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConsultaCepService {
    
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
