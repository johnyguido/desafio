package com.johny.desafio.services.impl;

import com.johny.desafio.mapper.EnderecoMapper;
import com.johny.desafio.mapper.PessoaMapper;
import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.dto.PessoaDTO;
import com.johny.desafio.model.entities.Endereco;
import com.johny.desafio.model.entities.Pessoa;
import com.johny.desafio.repositories.PessoaRepository;
import com.johny.desafio.services.PessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    public static final String PESSOA_NAO_ENCONTRADA = "Pessoa não encontrada com o ID ";

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private final EnderecoMapper enderecoMapper;

    @Transactional
    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaMapper.fromDto(pessoaDTO);
        List<Endereco> enderecos = pessoa.getEnderecos();

        if (enderecos != null && !enderecos.isEmpty()) {
            Pessoa finalPessoa = pessoa;
            enderecos.forEach(endereco -> endereco.setPessoa(finalPessoa));
        }
        pessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.toDto(pessoa);
    }


    public PessoaDTO consultarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PESSOA_NAO_ENCONTRADA + id));
        return pessoaMapper.toDto(pessoa);
    }

    @Transactional
    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);

        if (optionalPessoa.isPresent()) {
            Pessoa pessoa = optionalPessoa.get();
            BeanUtils.copyProperties(pessoaDTO, pessoa, "id");

            List<EnderecoDTO> enderecoDTOs = pessoaDTO.getEnderecos();
            if (enderecoDTOs != null && !enderecoDTOs.isEmpty()) {
                List<Endereco> enderecos = pessoaMapper.toEnderecos(enderecoDTOs);
                for (Endereco endereco : enderecos) {
                    endereco.setPessoa(pessoa);
                }
                pessoa.setEnderecos(enderecos);
            }

            Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
            return pessoaMapper.toDto(pessoaAtualizada);
        } else {
            throw new EntityNotFoundException("Pessoa não encontrada com o ID informado: " + id);
        }
    }

    public List<PessoaDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoaMapper.toPessoaDTOList(pessoas);
    }
    @Transactional
    public PessoaDTO criarEnderecoParaPessoa(Long idPessoa, EnderecoDTO enderecoDTO) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        Endereco endereco = enderecoMapper.toEndereco(enderecoDTO);
        endereco.setPessoa(pessoa);
        pessoa.getEnderecos().add(endereco);

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return pessoaMapper.toDto(pessoaSalva);
    }

    public List<EnderecoDTO> listarEnderecosDaPessoa(Long idPessoa) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        List<Endereco> enderecos = pessoa.getEnderecos();

        return enderecoMapper.toEnderecoDTOList(enderecos);
    }
    @Transactional
    public PessoaDTO atualizarEnderecoPrincipalDaPessoa(Long idPessoa, Long idEndereco) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntityNotFoundException(PESSOA_NAO_ENCONTRADA + idPessoa));

        Endereco endereco = pessoa.getEnderecos().stream()
                .filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com o ID " + idEndereco));

        pessoa.getEnderecos().forEach(e -> e.setEnderecoPrincipal(false));
        endereco.setEnderecoPrincipal(true);

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return pessoaMapper.toDto(pessoaSalva);
    }

    @Transactional
    public PessoaDTO editarEnderecos(Long idPessoa, Long idEndereco) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new EntityNotFoundException(PESSOA_NAO_ENCONTRADA + idPessoa));

        Endereco endereco = pessoa.getEnderecos().stream()
                .filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com o ID " + idEndereco));

        pessoa.getEnderecos().stream().forEach(end -> enderecoMapper.toEnderecoDTO(endereco));
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return pessoaMapper.toDto(pessoaSalva);
    }

}


