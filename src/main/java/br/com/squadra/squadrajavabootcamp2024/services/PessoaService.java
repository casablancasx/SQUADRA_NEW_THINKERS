package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.PessoaUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.EnderecoMapper;
import br.com.squadra.squadrajavabootcamp2024.mappers.PessoaMapper;
import br.com.squadra.squadrajavabootcamp2024.models.EnderecoModel;
import br.com.squadra.squadrajavabootcamp2024.models.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.EnderecoRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.PessoaRepository;
import br.com.squadra.squadrajavabootcamp2024.services.validators.BairroValidator;
import br.com.squadra.squadrajavabootcamp2024.services.validators.EnderecoValidator;
import br.com.squadra.squadrajavabootcamp2024.services.validators.PessoaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;
    private final PessoaMapper pessoaMapper;

    private final EnderecoValidator enderecoValidator;
    private final BairroValidator bairroValidator;
    private final PessoaValidator pessoaValidator;


    @Transactional
    public List<PessoaModel> cadastrarPessoa(PessoaCreateDTO request) {
        bairroValidator.verificarSeBairrosEstaoCadastradosNoBanco(request.getEnderecos());
        pessoaValidator.verificarDuplicidadeDeLogin(request.getLogin());
        PessoaModel pessoaModel = pessoaMapper.toEntity(request);
        pessoaRepository.save(pessoaModel);
        return pessoaRepository.findAllByOrderByCodigoPessoaDesc();
    }

    public Object buscarPessoaPorFiltro(Long codigoPessoa, String login, Integer status) {
        List<PessoaModel> pessoaModelList = pessoaRepository.findByFiltro(codigoPessoa, login, status);
        if (codigoPessoa != null) {
            return pessoaModelList.isEmpty() ? List.of() : pessoaMapper.toResponseDTO(pessoaModelList.get(0));
        }
        return pessoaModelList;
    }

    @Transactional
    public List<PessoaModel> atualizarPessoa(PessoaUpdateDTO pessoaAtualizada) {
        PessoaModel pessoaExistente = pessoaRepository.findById(pessoaAtualizada.getCodigoPessoa())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));
        enderecoValidator.verificarSeEnderecosEstaoCadastradosNoBanco(pessoaAtualizada.getEnderecos());
        List<EnderecoModel> listaDeEnderecosAtualizada = pessoaAtualizada.getEnderecos().stream().map(enderecoMapper::mapUpdateToEntity).toList();
        removeEnderecosCasoNaoExistaNaListaDeAtualizados(listaDeEnderecosAtualizada, pessoaExistente);
        pessoaMapper.atualizar(pessoaAtualizada, pessoaExistente);
        pessoaValidator.verificarDuplicidadeDeLoginExcetoParaPessoa(pessoaExistente.getLogin(), pessoaExistente.getCodigoPessoa());
        pessoaRepository.save(pessoaExistente);
        return pessoaRepository.findAllByOrderByCodigoPessoaDesc();
    }

    @Transactional
    public List<PessoaModel> deletarPessoa(Long codigoPessoa) {

        PessoaModel pessoa = pessoaRepository.findById(codigoPessoa)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));

        pessoaRepository.delete(pessoa);

        return pessoaRepository.findAllByOrderByCodigoPessoaDesc();
    }

    private void removeEnderecosCasoNaoExistaNaListaDeAtualizados(List<EnderecoModel> listaDeEnderecosAtualizada, PessoaModel pessoaExistente) {
        List<Long> listaDeEnderecosAtualizadaIds = listaDeEnderecosAtualizada.stream().map(EnderecoModel::getCodigoEndereco).toList();
        List<EnderecoModel> enderecosParaRemover = new ArrayList<>();

        for (EnderecoModel enderecoExistente : pessoaExistente.getEnderecos()) {
            if (!listaDeEnderecosAtualizadaIds.contains(enderecoExistente.getCodigoEndereco())) {
                enderecosParaRemover.add(enderecoExistente);
            }
        }

        pessoaExistente.getEnderecos().removeAll(enderecosParaRemover);
        enderecoRepository.deleteAll(enderecosParaRemover);
    }


}
