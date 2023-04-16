package com.johny.desafio.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class EnderecoDTO {
    private Long id;
    @NotNull
    private String logradouro;
    @NotNull
    private String cep;
    @NotNull
    private String numero;
    @NotNull
    private String cidade;
    private boolean enderecoPrincipal;
    @NotNull
    private Long pessoaId;
}

