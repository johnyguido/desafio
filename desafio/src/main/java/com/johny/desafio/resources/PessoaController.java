package com.johny.desafio.resources;

import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.dto.PessoaDTO;
import com.johny.desafio.services.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
@Api(value = "Pessoa API")
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    @ApiOperation(value = "Criar uma pessoa")
    public ResponseEntity<PessoaDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaCriada = pessoaService.criarPessoa(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar uma pessoa")
    public ResponseEntity<PessoaDTO> atualizarPessoa(@PathVariable Long id, @RequestBody
    PessoaDTO pessoaDTO) {
        PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDTO);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar uma pessoa")
    public ResponseEntity<PessoaDTO> consultarPessoa(@PathVariable Long id) {
        PessoaDTO pessoaConsultada = pessoaService.consultarPessoa(id);
        return ResponseEntity.ok(pessoaConsultada);
    }

    @GetMapping
    @ApiOperation(value = "Listar pessoas")
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<PessoaDTO> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("/{id}/enderecos")
    @ApiOperation(value = "Criar um endereço para uma pessoa")
    public ResponseEntity<PessoaDTO> criarEnderecoParaPessoa(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        PessoaDTO pessoaComEnderecoCriado = pessoaService.criarEnderecoParaPessoa(id, enderecoDTO);
        return ResponseEntity.ok(pessoaComEnderecoCriado);
    }

    @GetMapping("/{id}/enderecos")
    @ApiOperation(value = "Listar endereços de uma pessoa")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosDaPessoa(@PathVariable Long id) {
        List<EnderecoDTO> enderecos = pessoaService.listarEnderecosDaPessoa(id);
        return ResponseEntity.ok(enderecos);
    }

    @PutMapping("/{id}/enderecos/{enderecoId}/principal")
    @ApiOperation(value = "Atualizar o endereço principal de uma pessoa")
    public ResponseEntity<PessoaDTO> atualizarEnderecoPrincipalDaPessoa(@PathVariable Long id, @PathVariable Long enderecoId) {
        PessoaDTO pessoaComEnderecoAtualizado = pessoaService.atualizarEnderecoPrincipalDaPessoa(id, enderecoId);
        return ResponseEntity.ok(pessoaComEnderecoAtualizado);
    }

    @PutMapping("/{id}/enderecos/{enderecoId}")
    @ApiOperation(value = "Editar um endereço de uma pessoa")
    public ResponseEntity<PessoaDTO> editarEnderecos(@PathVariable Long id, @PathVariable Long enderecoId) {
        PessoaDTO pessoaComEnderecoAtualizado = pessoaService.editarEnderecos(id, enderecoId);
        return ResponseEntity.ok(pessoaComEnderecoAtualizado);
    }

}


