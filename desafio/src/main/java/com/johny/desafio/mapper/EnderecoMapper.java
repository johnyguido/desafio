package com.johny.desafio.mapper;

import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.entities.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    EnderecoDTO toEnderecoDTO(Endereco endereco);

    Endereco toEndereco(EnderecoDTO enderecoDTO);

    List<EnderecoDTO> toEnderecoDTOList(List<Endereco> enderecos);

}
