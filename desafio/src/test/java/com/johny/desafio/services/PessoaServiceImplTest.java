package com.johny.desafio.services;

import com.johny.desafio.mapper.EnderecoMapper;
import com.johny.desafio.mapper.EnderecoMapperImpl;
import com.johny.desafio.mapper.PessoaMapper;
import com.johny.desafio.mapper.PessoaMapperImpl;
import com.johny.desafio.mock.EnderecoMock;
import com.johny.desafio.mock.PessoaMock;
import com.johny.desafio.model.dto.EnderecoDTO;
import com.johny.desafio.model.dto.PessoaDTO;
import com.johny.desafio.model.entities.Endereco;
import com.johny.desafio.model.entities.Pessoa;
import com.johny.desafio.repositories.PessoaRepository;
import com.johny.desafio.services.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

import static com.johny.desafio.mock.EnderecoMock.getEnderecoDTOMock;
import static com.johny.desafio.mock.EnderecoMock.getMockEndereco;
import static com.johny.desafio.mock.PessoaMock.getPessoaDTOMock;
import static com.johny.desafio.mock.PessoaMock.getPessoaMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceImplTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Spy
    private PessoaMapper pessoaMapper = new PessoaMapperImpl();

    @Spy
    private EnderecoMapper enderecoMapper = new EnderecoMapperImpl();

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    @Before
    public void setUp() {
    }

    @Test
    public void criarPessoaTest() {

        when(pessoaRepository.save(any())).thenReturn(getPessoaMock());

        PessoaDTO result = pessoaService.criarPessoa(getPessoaDTOMock);

        assertThat(result).isNotNull();
    }

    @Test
    public void consultarPessoaTest() {

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(getPessoaMock()));

        PessoaDTO result = pessoaService.consultarPessoa(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(getPessoaMock().getId());
        assertThat(result.getNome()).isEqualTo(getPessoaMock().getNome());

        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    public void atualizarPessoaTest() {
        Long id = 1L;
        PessoaDTO pessoaDTOMock = PessoaDTO.builder()
                .nome("João da Silva")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .enderecos(Collections.singletonList(EnderecoDTO.builder()
                        .logradouro("Rua B")
                        .cep("98765-432")
                        .numero("321")
                        .cidade("Rio de Janeiro")
                        .enderecoPrincipal(false)
                        .build()))
                .build();

        Pessoa pessoaMock = Pessoa.builder()
                .id(1L)
                .nome("João da Silva")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .enderecos(Collections.singletonList(Endereco.builder()
                        .id(1L)
                        .logradouro("Rua B")
                        .cep("98765-432")
                        .numero("321")
                        .cidade("Rio de Janeiro")
                        .enderecoPrincipal(false)
                        .build()))
                .build();

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoaMock));
        when(pessoaRepository.save(pessoaMock)).thenReturn(pessoaMock);

        PessoaDTO result = pessoaService.atualizarPessoa(id, pessoaDTOMock);

        assertThat(result).isNotNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void atualizarPessoaIdInexistenteTest() {
        Long idInexistente = 99L;
        PessoaDTO pessoaDTO = PessoaDTO.builder()
                .nome("João da Silva")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .build();

        pessoaService.atualizarPessoa(idInexistente, pessoaDTO);
    }
    @Test
    public void listarPessoasTest() {
        // Given
        List<Pessoa> pessoasMock = Arrays.asList(getPessoaMock());
        when(pessoaRepository.findAll()).thenReturn(pessoasMock);

        // When
        List<PessoaDTO> result = pessoaService.listarPessoas();

        // Then
        assertThat(result).isNotNull();
    }

    @Test
    public void testCriarEnderecoParaPessoaTest() {
        // Given
        Long idPessoa = 1L;

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(getPessoaMock()));
        when(pessoaRepository.save(any())).thenReturn(getPessoaMock());

        // When
        PessoaDTO pessoaDTO = pessoaService.criarEnderecoParaPessoa(idPessoa, getEnderecoDTOMock());

        // Then
        assertThat(pessoaDTO).isNotNull();
    }

    @Test
    public void deveRetornarListaVaziaQuandoPessoaNaoTiverEnderecoTest() {
        Long idPessoa = 1L;
        Pessoa pessoaMock = PessoaMock.getPessoaMock();
        pessoaMock.setEnderecos(new ArrayList<>());
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaMock));

        List<EnderecoDTO> enderecos = pessoaService.listarEnderecosDaPessoa(idPessoa);

        assertTrue(enderecos.isEmpty());
    }

    @Test
    public void testAtualizarEnderecoPrincipalDaPessoaTest() {
        // Criação dos mocks
        Pessoa pessoa = PessoaMock.getPessoaMock();
        Endereco endereco1 = EnderecoMock.getMockEndereco();
        endereco1.setEnderecoPrincipal(true);
        Endereco endereco2 = EnderecoMock.getMockEndereco();
        endereco2.setId(2L);
        endereco2.setEnderecoPrincipal(false);
        pessoa.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

        PessoaDTO pessoaDTO = getPessoaDTOMock;
        pessoaDTO.setEnderecos(Arrays.asList(getEnderecoDTOMock()));

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any())).thenReturn(getPessoaMock());

        PessoaDTO result = pessoaService.atualizarEnderecoPrincipalDaPessoa(1L, 2L);

        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertFalse(result.getEnderecos().stream().anyMatch(getEnderecoDTOMock()::equals));
        assertFalse(result.getEnderecos().stream().anyMatch(enderecoDTO -> enderecoDTO.isEnderecoPrincipal()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEditarEnderecosPessoaNaoEncontrada() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        pessoaService.editarEnderecos(1L, 1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEditarEnderecosEnderecoNaoEncontrado() {
        Pessoa pessoa = PessoaMock.getPessoaMock();

        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        pessoaService.editarEnderecos(pessoa.getId(), 1L);
    }

    @Test
    public void testEditarEnderecosEnderecoNulo() {
        Pessoa pessoa = PessoaMock.getPessoaMock();
        pessoa.setEnderecos(Arrays.asList(getMockEndereco()));

        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any())).thenReturn(getPessoaMock());

        PessoaDTO pessoaDTO = pessoaService.editarEnderecos(pessoa.getId(), 1L);

        assertEquals(0, pessoaDTO.getEnderecos().size());

        verify(pessoaRepository).save(pessoa);
    }

    @Test
    public void testEditarEnderecosEnderecoValido() {
        Pessoa pessoa = getPessoaMock();
        pessoa.setEnderecos(Arrays.asList(getMockEndereco()));

        Endereco endereco = EnderecoMock.getMockEndereco();

        when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any())).thenReturn(getPessoaMock());


        PessoaDTO pessoaDTO = pessoaService.editarEnderecos(pessoa.getId(), endereco.getId());

        assertTrue(endereco.isEnderecoPrincipal());

        verify(pessoaRepository, times(1)).save(pessoa);
    }

}
