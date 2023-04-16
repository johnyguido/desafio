package com.johny.desafio.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaDTO {

    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private LocalDate dataNascimento;
    private List<EnderecoDTO> enderecos;
}

