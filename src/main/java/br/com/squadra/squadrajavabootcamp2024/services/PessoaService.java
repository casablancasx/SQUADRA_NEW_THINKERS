package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.EnderecoCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.EnderecoUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.PessoaUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.EnderecoMapper;
import br.com.squadra.squadrajavabootcamp2024.mappers.PessoaMapper;
import br.com.squadra.squadrajavabootcamp2024.entities.EnderecoEntity;
import br.com.squadra.squadrajavabootcamp2024.entities.PessoaEntity;
import br.com.squadra.squadrajavabootcamp2024.repositories.BairroRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.EnderecoRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepository;
    private final BairroRepository bairroRepository;

    private final PessoaMapper pessoaMapper;


    @Transactional
    public List<PessoaEntity> cadastrarPessoa(PessoaCreateDTO request) {

        validarSeBairrosEstaoCadastradosNoBancoDeDados(request.getEnderecos());

        verificarDuplicidadeDeLogin(request.getLogin());

        PessoaEntity pessoaEntity = pessoaMapper.toEntity(request);

        pessoaRepository.save(pessoaEntity);

        return pessoaRepository.findAllByOrderByCodigoPessoaDesc();
    }

    public Object buscarPessoaPorFiltro(Long codigoPessoa, String login, Integer status) {

        List<PessoaEntity> pessoaEntityList = pessoaRepository.findByFiltro(codigoPessoa, login, status);

        if (codigoPessoa != null) {
            return pessoaEntityList.isEmpty() ? List.of() : pessoaMapper.toResponseDTO(pessoaEntityList.get(0));
        }

        return pessoaEntityList;
    }

    @Transactional
    public List<PessoaEntity> atualizarPessoa(PessoaUpdateDTO pessoaAtualizada) {

        PessoaEntity pessoaExistente = pessoaRepository.findById(pessoaAtualizada.getCodigoPessoa())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));

        verificarSeEnderecosExistem(pessoaAtualizada.getEnderecos());

        List<EnderecoEntity> listaDeEnderecosAtualizada = pessoaAtualizada.getEnderecos().stream().map(enderecoMapper::mapUpdateToEntity).toList();

        removeEnderecosCasoNaoExistaNaListaDeAtualizados(listaDeEnderecosAtualizada, pessoaExistente);

        pessoaMapper.atualizar(pessoaAtualizada, pessoaExistente);

        verificarDuplicidadeDeLoginExcetoParaPessoa(pessoaExistente.getLogin(), pessoaExistente.getCodigoPessoa());

        pessoaRepository.save(pessoaExistente);

        return pessoaRepository.findAllByOrderByCodigoPessoaDesc();
    }

    @Transactional
    public void deletarPessoa(Long codigoPessoa) {

        PessoaEntity entity = pessoaRepository.findById(codigoPessoa)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));

        pessoaRepository.delete(entity);
    }

    private void removeEnderecosCasoNaoExistaNaListaDeAtualizados(List<EnderecoEntity> listaDeEnderecosAtualizada, PessoaEntity pessoaExistente) {
        List<Long> listaDeEnderecosAtualizadaIds = listaDeEnderecosAtualizada.stream().map(EnderecoEntity::getCodigoEndereco).toList();
        List<EnderecoEntity> enderecosParaRemover = new ArrayList<>();

        for (EnderecoEntity enderecoExistente : pessoaExistente.getEnderecos()) {
            if (!listaDeEnderecosAtualizadaIds.contains(enderecoExistente.getCodigoEndereco())) {
                enderecosParaRemover.add(enderecoExistente);
            }
        }

        pessoaExistente.getEnderecos().removeAll(enderecosParaRemover);
        enderecoRepository.deleteAll(enderecosParaRemover);
    }

    private void verificarDuplicidadeDeLoginExcetoParaPessoa(String login, Long codigoPessoa) {
        if (pessoaRepository.existsByLoginAndAndCodigoPessoaNot(login, codigoPessoa)) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar pessoa no banco de dados. Já existe uma pessoa de login " + login + " cadastrado.");
        }
    }

    private void verificarDuplicidadeDeLogin(String login) {
        if (pessoaRepository.existsByLogin(login)) {
            throw new ResourceAlreadyExistException("Não foi possível incluir pessoa no banco de dados. Já existe uma pessoa de login " + login + " cadastrado.");
        }
    }

    private void verificarSeEnderecosExistem(List<EnderecoUpdateDTO> enderecosAtualizados) {
        for (EnderecoUpdateDTO endereco : enderecosAtualizados) {
            if (!enderecoRepository.existsByCodigoEndereco(endereco.getCodigoEndereco()) && endereco.getCodigoEndereco() != null) {
                throw new ResourceNotFoundException("Não foi possível incluir pessoa no banco de dados. Não existe um endereço com código " + endereco.getCodigoEndereco() + " cadastrado.");
            }
        }
    }

    private void validarSeBairrosEstaoCadastradosNoBancoDeDados(List<EnderecoCreateDTO> enderecos) {
        for (EnderecoCreateDTO endereco : enderecos) {
            if (!bairroRepository.existsByCodigoBairro(endereco.getCodigoBairro())) {
                throw new ResourceNotFoundException("Não foi possível incluir pessoa no banco de dados. Não existe um bairro com código " + endereco.getCodigoBairro() + " cadastrado.");
            }
        }
    }
}
