package com.grupo4.projetofinalapi.typeconverters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.grupo4.projetofinalapi.entities.Produto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StringToProdutoConverter implements Converter<String, Produto> {

    @Override
    public Produto convert(String s) {
        try {
            return new ObjectMapper().registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule())
                    .readValue(s, Produto.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
