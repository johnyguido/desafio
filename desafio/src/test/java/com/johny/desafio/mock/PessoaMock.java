package com.johny.desafio.mock;

import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.dto.PessoaDTO;
import com.johny.desafio.model.entities.Pessoa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class PessoaMock {

    public static PessoaDTO getPessoaDTOMock = PessoaDTO.builder()
            .id(1L)
            .nome("João da Silva")
            .dataNascimento(LocalDate.of(1990, 5, 15))
            .enderecos(Arrays.asList(
                    EnderecoDTO.builder()
                            .logradouro("Rua A")
                            .numero("123")
                            .cidade("São Paulo")
                            .cep("01234-567")
                            .build(),
                    EnderecoDTO.builder()
                            .logradouro("Rua B")
                            .numero("456")
                            .cidade("São Paulo")
                            .cep("04567-890")
                            .build()
            ))
            .build();

    public static Pessoa getPessoaMock() {
        return Pessoa.builder()
                .id(1L)
                .nome("João")
                .dataNascimento(LocalDate.of(1990, 5, 10))
                .enderecos(new ArrayList<>())
                .build();
    }


}
