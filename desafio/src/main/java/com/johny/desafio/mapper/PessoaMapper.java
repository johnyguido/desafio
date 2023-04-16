package com.johny.desafio.mapper;

import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.dto.PessoaDTO;
import com.johny.desafio.model.entities.Endereco;
import com.johny.desafio.model.entities.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    @Mapping(target = "enderecos", source = "enderecos")
    PessoaDTO toDto(Pessoa pessoa);

    Pessoa fromDto(PessoaDTO pessoaDTO);

    @Mapping(target = "pessoaId", source = "pessoa.id")
    EnderecoDTO toEnderecoDTO(Endereco endereco);

    Endereco fromEnderecoDTO(EnderecoDTO enderecoDTO);

    default List<Endereco> toEnderecos(List<EnderecoDTO> enderecoDTOs) {
        if (enderecoDTOs == null) {
            return Collections.emptyList();
        }

        return enderecoDTOs.stream()
                .map(this::fromEnderecoDTO)
                .collect(Collectors.toList());
    }

    default List<EnderecoDTO> toEnderecoDTOs(List<Endereco> enderecos) {
        if (enderecos == null) {
            return Collections.emptyList();
        }

        return enderecos.stream()
                .map(this::toEnderecoDTO)
                .collect(Collectors.toList());
    }

    default List<PessoaDTO> toPessoaDTOList(List<Pessoa> pessoaList) {
        if (pessoaList == null) {
            return Collections.emptyList();
        }

        return pessoaList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default Long toPessoaId(Pessoa pessoa) {
        return pessoa.getId();
    }

}


