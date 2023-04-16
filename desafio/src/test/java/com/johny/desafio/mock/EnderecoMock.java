package com.johny.desafio.mock;

import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.entities.Endereco;
import com.johny.desafio.model.entities.Pessoa;

public class EnderecoMock {

    public static Endereco getMockEndereco() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setLogradouro("Rua das Flores");
        endereco.setCep("12345-678");
        endereco.setNumero("100");
        endereco.setCidade("São Paulo");
        endereco.setEnderecoPrincipal(true);

        Pessoa pessoa = PessoaMock.getPessoaMock();
        endereco.setPessoa(pessoa);

        return endereco;
    }

    public static EnderecoDTO getEnderecoDTOMock() {
        return EnderecoDTO.builder()
                .id(1L)
                .logradouro("Rua das Flores")
                .cep("12345-678")
                .numero("100")
                .cidade("São Paulo")
                .enderecoPrincipal(true)
                .pessoaId(1L)
                .build();
    }

}
