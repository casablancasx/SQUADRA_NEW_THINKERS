package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.EnderecoCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.EnderecoUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.PessoaUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.entities.EnderecoEntity;
import br.com.squadra.squadrajavabootcamp2024.entities.PessoaEntity;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.PessoaMapper;
import br.com.squadra.squadrajavabootcamp2024.repositories.BairroRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.EnderecoRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
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


    public List<PessoaEntity> atualizarPessoa(PessoaUpdateDTO pessoaAtualizada) {

        PessoaEntity pessoaExistente = pessoaRepository.findById(pessoaAtualizada.getCodigoPessoa())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));

        verificarSeEnderecosExistem(pessoaAtualizada.getEnderecos());


        List<Long> idsEnderecosAtualizados = pessoaAtualizada.getEnderecos()
                .stream().map(EnderecoUpdateDTO::getCodigoEndereco)
                .filter(Objects::nonNull).toList();


        removeEnderecosCasoNaoExistaNaListaDeAtualizados(idsEnderecosAtualizados, pessoaExistente);

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

    private void removeEnderecosCasoNaoExistaNaListaDeAtualizados(List<Long> idsEnderecosAtualizados, PessoaEntity pessoaExistente) {
        List<EnderecoEntity> enderecosParaRemover = new ArrayList<>();

        pessoaExistente.getEnderecos().stream()
                .filter(endereco -> !idsEnderecosAtualizados.contains(endereco.getCodigoEndereco()))
                .forEach(enderecosParaRemover::add);

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
        enderecosAtualizados.forEach(endereco ->{
            Long codigoEndereco = endereco.getCodigoEndereco();
            if (!enderecoRepository.existsByCodigoEndereco(codigoEndereco) && codigoEndereco != null) {
                throw new ResourceNotFoundException("Não foi possível incluir pessoa no banco de dados. Não existe um endereço com código " + codigoEndereco + " cadastrado.");
            }
        });
    }

   private void validarSeBairrosEstaoCadastradosNoBancoDeDados(List<EnderecoCreateDTO> enderecos) {
       enderecos.forEach(endereco -> {
           Long codigoBairro = endereco.getCodigoBairro();
           if (!bairroRepository.existsByCodigoBairro(codigoBairro) && codigoBairro != null) {
               throw new ResourceNotFoundException("Não foi possível incluir pessoa no banco de dados. Não existe um bairro com código " + codigoBairro + " cadastrado.");
           }
       });
   }
}
