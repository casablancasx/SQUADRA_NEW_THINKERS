package br.com.squadra.squadrajavabootcamp2024.services;

import br.com.squadra.squadrajavabootcamp2024.dtos.update.EnderecoUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.update.PessoaUpdateDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceAlreadyExistException;
import br.com.squadra.squadrajavabootcamp2024.dtos.create.PessoaCreateDTO;
import br.com.squadra.squadrajavabootcamp2024.dtos.response.PessoaResponseDTO;
import br.com.squadra.squadrajavabootcamp2024.exceptions.ResourceNotFoundException;
import br.com.squadra.squadrajavabootcamp2024.mappers.EnderecoMapper;
import br.com.squadra.squadrajavabootcamp2024.mappers.PessoaMapper;
import br.com.squadra.squadrajavabootcamp2024.models.EnderecoModel;
import br.com.squadra.squadrajavabootcamp2024.models.PessoaModel;
import br.com.squadra.squadrajavabootcamp2024.repositories.EnderecoRepository;
import br.com.squadra.squadrajavabootcamp2024.repositories.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepository;


    public List<PessoaModel> cadastrarPessoa(PessoaCreateDTO request) {

        if (pessoaRepository.existsByLogin(request.getLogin())) {
            throw new ResourceAlreadyExistException("Não foi possível incluir pessoa no banco de dados. Já existe uma pessoa de login " + request.getLogin() + " cadastrado.");
        }

        PessoaModel pessoaModel = pessoaMapper.toEntity(request);
        pessoaModel.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoaModel));
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

    public List<PessoaModel> atualizarPessoa(PessoaUpdateDTO pessoaAtualizada) {
        PessoaModel pessoaExistente = pessoaRepository.findById(pessoaAtualizada.getCodigoPessoa())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));


        validarEnderecos(pessoaAtualizada.getEnderecos());
        List<EnderecoModel> listaDeEnderecosAtualizada = pessoaAtualizada.getEnderecos().stream().map(enderecoMapper::mapUpdateToEntity).toList();
        removeEnderecosCasoNaoExistaNaListaDeAtualizados(listaDeEnderecosAtualizada, pessoaExistente);
        pessoaMapper.atualizar(pessoaAtualizada, pessoaExistente);


        if (pessoaRepository.existsByLoginAndAndCodigoPessoaNot(pessoaExistente.getLogin(), pessoaExistente.getCodigoPessoa())) {
            throw new ResourceAlreadyExistException("Não foi possível atualizar pessoa no banco de dados. Já existe uma pessoa de login " + pessoaExistente.getLogin() + " cadastrado.");
        }

        pessoaRepository.save(pessoaExistente);
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

    private void validarEnderecos(List<EnderecoUpdateDTO> enderecosAtualizados) {
        for (EnderecoUpdateDTO endereco : enderecosAtualizados) {
            if (endereco.getCodigoEndereco() != null) {
                enderecoRepository.findById(endereco.getCodigoEndereco()).orElseThrow(() -> new ResourceNotFoundException("id nao encontrado"));
            }
        }
    }


}
