package com.johny.desafio.services;

import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.dto.PessoaDTO;

import java.util.List;

public interface PessoaService {

    PessoaDTO criarPessoa(PessoaDTO pessoaDTO);

    PessoaDTO consultarPessoa(Long id);

    PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO);

    List<PessoaDTO> listarPessoas();

    PessoaDTO criarEnderecoParaPessoa(Long idPessoa, EnderecoDTO enderecoDTO);

    List<EnderecoDTO> listarEnderecosDaPessoa(Long idPessoa);

    PessoaDTO atualizarEnderecoPrincipalDaPessoa(Long idPessoa, Long idEndereco);

    PessoaDTO editarEnderecos(Long idPessoa, Long idEndereco);
}

